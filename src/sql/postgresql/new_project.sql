/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created
 *@version    $Id$
 */
 
CREATE TABLE groups (
	group_id SERIAL PRIMARY KEY ,
	group_name VARCHAR(50) NOT NULL ,
	enabled BOOLEAN DEFAULT false NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  expiration TIMESTAMP DEFAULT NULL
);

CREATE INDEX "groups_idx" ON "groups" USING btree ("group_id");


CREATE TABLE users (
	user_id SERIAL PRIMARY KEY ,
  group_id INTEGER DEFAULT 0 NOT NULL ,
	department_id INTEGER DEFAULT 0 NULL ,
	first_name VARCHAR(50) NULL ,
	last_name VARCHAR(50) NULL ,
	username VARCHAR(50) NOT NULL ,
	password VARCHAR(50) NOT NULL ,
	company VARCHAR(50) NULL ,
	email VARCHAR(50) NULL ,
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
	enteredby INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT false NULL ,
  start_page INTEGER DEFAULT 1 NOT NULL ,
	access_personal BOOLEAN DEFAULT false NULL ,
	access_enterprise BOOLEAN DEFAULT false NULL ,
	access_admin BOOLEAN DEFAULT false NULL ,
	access_inbox BOOLEAN DEFAULT false NOT NULL ,
	access_resources BOOLEAN DEFAULT false NOT NULL ,
	last_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
  expiration TIMESTAMP DEFAULT NULL
);

CREATE TABLE user_groups (
	user_id INTEGER NOT NULL ,
	group_id INTEGER NOT NULL 
);

CREATE TABLE departments (
	code SERIAL PRIMARY KEY,
	group_id integer NOT NULL DEFAULT 0,
	description VARCHAR(100) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT false NOT NULL
);


CREATE TABLE project_assignments (
	assignment_id SERIAL PRIMARY KEY,
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
	assign_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  est_start_date TIMESTAMP NULL,
	start_date TIMESTAMP NULL,
	due_date TIMESTAMP NULL,
  status_id INTEGER NOT NULL,
	status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	complete_date TIMESTAMP NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE INDEX "project_assignments_idx" ON "project_assignments"
  USING btree ("activity_id");
  
CREATE INDEX "project_assignments_cidx" ON "project_assignments" 
  USING btree ("complete_date", "user_assign_id");
  
CREATE TABLE project_assignments_status (
	status_id SERIAL PRIMARY KEY,
	assignment_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	description TEXT NOT NULL,
	status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE project_inbox (
	message_id INTEGER NOT NULL ,
	user_id INTEGER NOT NULL ,
	read TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	addressee_type INTEGER NOT NULL 
);

CREATE TABLE project_inbox_items (
	message_id SERIAL PRIMARY KEY ,
	user_id INTEGER NOT NULL ,
	project_id INTEGER NOT NULL ,
	message_date TIMESTAMP NOT NULL ,
	message_category INTEGER NOT NULL ,
	message_subject VARCHAR(255) NOT NULL ,
	message_body TEXT NOT NULL 
);

CREATE TABLE project_issues (
	issue_id SERIAL PRIMARY KEY,
	project_id INTEGER NOT NULL,
	type_id INTEGER NOT NULL,
	subject VARCHAR(255) NOT NULL,
	message TEXT NOT NULL,
  importance INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
	entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE INDEX "project_issues_limit_idx"
  ON "project_issues"
  USING btree ("type_id", "project_id", "enteredby");
  
CREATE INDEX "project_issues_idx"
  ON "project_issues"
  USING btree ("issue_id");  
  
  
CREATE TABLE project_issue_replies (
	reply_id SERIAL PRIMARY KEY ,
	issue_id INTEGER NOT NULL ,
  reply_to INTEGER DEFAULT 0 ,
	subject VARCHAR(50) NOT NULL ,
	message TEXT NOT NULL ,
  importance INTEGER DEFAULT 0,
	entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE TABLE project_folders (
  folder_id SERIAL PRIMARY KEY,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  subject VARCHAR(255) NOT NULL,
  description TEXT,
  parent INT NOT NULL DEFAULT 0
);
  
/* project_id will be replaced by link_item_id */
CREATE TABLE project_files (
	item_id SERIAL PRIMARY KEY ,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
	project_id INTEGER NOT NULL , 
  folder_id INTEGER DEFAULT 0,
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
	subject VARCHAR(500) NOT NULL ,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 , 
  enabled BOOLEAN DEFAULT TRUE ,
  downloads INTEGER DEFAULT 0,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE INDEX "project_files_cidx" ON "project_files" 
  USING btree ("link_module_id", "link_item_id");

CREATE TABLE project_files_version (
	item_id INTEGER,
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
	subject VARCHAR(500) NOT NULL ,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 ,
  enabled BOOLEAN DEFAULT TRUE ,
  downloads INTEGER DEFAULT 0,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE TABLE project_files_download (
	item_id INTEGER,
  version FLOAT DEFAULT 0 ,
  user_download_id INTEGER NOT NULL,
  download_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE lookup_project_templates (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_project_activity (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  template_id INTEGER DEFAULT 0
);

CREATE TABLE lookup_project_priority (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  graphic VARCHAR(75),
  type INTEGER NOT NULL
);


CREATE TABLE lookup_project_issues (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_project_status (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  graphic VARCHAR(75),
  type INTEGER NOT NULL
);

CREATE TABLE lookup_project_loe (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  base_value INTEGER DEFAULT 0 NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE project_requirements (
	requirement_id SERIAL PRIMARY KEY,
	project_id INTEGER NOT NULL,
	submittedBy VARCHAR(50) NOT NULL,
	departmentBy VARCHAR(30) NULL,
	shortDescription VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	dateReceived TIMESTAMP NULL,
	estimated_loevalue INTEGER DEFAULT -1 NOT NULL,
  estimated_loetype INTEGER DEFAULT -1,
  actual_loevalue INTEGER DEFAULT -1 NOT NULL,
  actual_loetype INTEGER DEFAULT -1,
	deadline TIMESTAMP NULL,
  approvedBy INTEGER DEFAULT -1,
  approvalDate TIMESTAMP NULL,
  closedBy INTEGER DEFAULT -1,
	closeDate TIMESTAMP NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE TABLE projects (
	project_id SERIAL PRIMARY KEY ,
	group_id INTEGER NOT NULL ,
	department_id INTEGER NOT NULL ,
  template_id INTEGER NOT NULL DEFAULT 0,
	title VARCHAR(100) NOT NULL ,
	shortDescription VARCHAR(200) NOT NULL ,
	requestedBy VARCHAR(50) NULL ,
	requestedDept VARCHAR(50) NULL ,
	requestDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ,
	approvalDate TIMESTAMP NULL ,
	closeDate TIMESTAMP NULL,
  owner INTEGER NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL
);

CREATE INDEX "projects_idx"
  ON "projects"
  USING btree ("group_id", "project_id");



CREATE TABLE project_timesheet (
	timesheet_id SERIAL PRIMARY KEY ,
	user_id INTEGER NOT NULL ,
	project_id INTEGER NOT NULL ,
	start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
	stop_time TIMESTAMP NULL 
);

CREATE TABLE project_team (
	project_id INTEGER NOT NULL ,
	user_id INTEGER NOT NULL ,
	userLevel INTEGER DEFAULT 0 NOT NULL ,
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER DEFAULT 0 NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER DEFAULT 0 NOT NULL
);


