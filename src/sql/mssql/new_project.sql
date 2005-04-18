/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    March 27, 2002
 *@version    $Id$
 */

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

CREATE TABLE lookup_project_role (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_project_category (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(80) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_news_template (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0,
  load_article BIT DEFAULT 0,
  load_project_article_list BIT DEFAULT 0,
  load_article_linked_list BIT DEFAULT 0,
  load_public_projects BIT DEFAULT 0,
  load_article_category_list BIT DEFAULT 0,
  mapped_jsp VARCHAR(255) NOT NULL
);


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
  approvalDate DATETIME NULL,
  closeDate DATETIME NULL,
  owner INTEGER NULL,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  approvalBy INTEGER NULL REFERENCES access(user_id),
  category_id INTEGER NULL REFERENCES lookup_project_category(code),
  portal BIT NOT NULL DEFAULT 0,
  allow_guests BIT NOT NULL DEFAULT 0,
  news_enabled BIT NOT NULL DEFAULT 1,
  details_enabled BIT NOT NULL DEFAULT 1,
  team_enabled BIT NOT NULL DEFAULT 1,
  plan_enabled BIT NOT NULL DEFAULT 1,
  lists_enabled BIT NOT NULL DEFAULT 1,
  discussion_enabled BIT NOT NULL DEFAULT 1,
  tickets_enabled BIT NOT NULL DEFAULT 1,
  documents_enabled BIT NOT NULL DEFAULT 1,
  news_label VARCHAR(50) NULL,
  details_label VARCHAR(50) NULL,
  team_label VARCHAR(50) NULL,
  plan_label VARCHAR(50) NULL,
  lists_label VARCHAR(50) NULL,
  discussion_label VARCHAR(50) NULL,
  tickets_label VARCHAR(50) NULL,
  documents_label VARCHAR(50) NULL,
  est_closedate DATETIME,
  budget FLOAT,
  budget_currency VARCHAR(5),
  requestDate_timezone VARCHAR(255),
  est_closedate_timezone VARCHAR(255),
  portal_default BIT NOT NULL DEFAULT 0,
  portal_header VARCHAR(255),
  portal_format VARCHAR(255),
  portal_key VARCHAR(255),
  portal_build_news_body BIT NOT NULL DEFAULT 0,
  portal_news_menu BIT NOT NULL DEFAULT 0,
  description TEXT,
  allows_user_observers BIT NOT NULL DEFAULT 0,
  level INTEGER NOT NULL DEFAULT 10,
  portal_page_type INTEGER,
  calendar_enabled BIT NOT NULL DEFAULT 1,
  calendar_label VARCHAR(50) NULL,
  accounts_enabled BIT NOT NULL DEFAULT 1,
  accounts_label VARCHAR(50) NULL
);

CREATE INDEX "projects_idx"
  ON "projects"
  ("group_id", "project_id");

  
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
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  startdate DATETIME NULL,
  startdate_timezone VARCHAR(255),
  deadline_timezone VARCHAR(255),
  due_date_timezone VARCHAR(255)
);

CREATE TABLE project_assignments_folder (
  folder_id INT IDENTITY PRIMARY KEY,
  parent_id INTEGER NULL REFERENCES project_assignments_folder(folder_id),
  requirement_id INTEGER NOT NULL REFERENCES project_requirements(requirement_id),
  name VARCHAR(255) NOT NULL,
  description TEXT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);

CREATE TABLE project_assignments (
  assignment_id INT IDENTITY PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  requirement_id INTEGER NULL REFERENCES project_requirements(requirement_id),
  assignedBy INTEGER REFERENCES access(user_id),
  user_assign_id INTEGER NULL REFERENCES access(user_id),
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
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  folder_id INTEGER NULL REFERENCES project_assignments_folder(folder_id),
  percent_complete INTEGER NULL,
  due_date_timezone VARCHAR(255) NULL
);

CREATE INDEX "project_assignments_cidx" ON "project_assignments"
  ("complete_date", "user_assign_id");
  
CREATE INDEX proj_assign_req_id_idx ON project_assignments (requirement_id);
  
CREATE TABLE project_assignments_status (
  status_id INT IDENTITY PRIMARY KEY,
  assignment_id INTEGER NOT NULL REFERENCES project_assignments,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  description TEXT NOT NULL,
  status_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  percent_complete INTEGER NULL,
  project_status_id INTEGER REFERENCES lookup_project_status,
  user_assign_id INTEGER NULL REFERENCES access(user_id)
);


CREATE TABLE project_issues_categories (
  category_id INT IDENTITY PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  subject VARCHAR(255) NOT NULL,
  description TEXT NULL,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  topics_count INTEGER NOT NULL DEFAULT 0,
  posts_count INTEGER NOT NULL DEFAULT 0,
  last_post_date DATETIME,
  last_post_by INTEGER,
  allow_files BIT NOT NULL DEFAULT 0
);

CREATE TABLE project_issues (
  issue_id INT IDENTITY PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  subject VARCHAR(255) NOT NULL,
  message TEXT NOT NULL,
  importance INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  category_id INTEGER NULL REFERENCES project_issues_categories(category_id),
  reply_count INTEGER NOT NULL DEFAULT 0,
  last_reply_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_reply_by INTEGER
);

CREATE TABLE project_issue_replies (
  reply_id INT IDENTITY PRIMARY KEY ,
  issue_id INTEGER NOT NULL REFERENCES project_issues,
  reply_to INTEGER DEFAULT 0 ,
  subject VARCHAR(255) NOT NULL ,
  message TEXT NOT NULL ,
  importance INTEGER NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);

CREATE TABLE project_folders (
  folder_id INT IDENTITY PRIMARY KEY,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  subject VARCHAR(255) NOT NULL,
  description TEXT,
  parent_id INT NULL,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  display INTEGER NULL
);

CREATE TABLE project_files (
  item_id INT IDENTITY PRIMARY KEY ,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  folder_id INTEGER NULL REFERENCES project_folders,
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
  subject VARCHAR(500) NOT NULL ,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 , 
  enabled BIT DEFAULT 1 ,
  downloads INTEGER DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  default_file BIT DEFAULT 0
);

CREATE INDEX "project_files_cidx" ON "project_files" 
  ("link_module_id", "link_item_id");

CREATE TABLE project_files_version (
  item_id INTEGER REFERENCES project_files(item_id),
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
  subject VARCHAR(500) NOT NULL ,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 ,
  enabled BIT DEFAULT 1 ,
  downloads INTEGER DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);

CREATE TABLE project_files_download (
  item_id INTEGER NOT NULL REFERENCES project_files(item_id),
  version FLOAT DEFAULT 0 ,
  user_download_id INTEGER NULL REFERENCES access(user_id),
  download_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE project_files_thumbnail (
  item_id INTEGER REFERENCES project_files(item_id),
  filename VARCHAR(255) NOT NULL,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 ,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);


CREATE TABLE project_team (
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  userlevel INTEGER NOT NULL REFERENCES lookup_project_role(code),
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  status INTEGER NULL,
  last_accessed DATETIME
);
CREATE UNIQUE INDEX project_team_uni_idx ON project_team (project_id, user_id);

CREATE TABLE project_news_category (
  category_id INT IDENTITY PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  category_name VARCHAR(255),
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  level INTEGER NOT NULL DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE project_news (
  news_id INT IDENTITY PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  category_id INTEGER NULL REFERENCES project_news_category(category_id),
  subject VARCHAR(255) NOT NULL,
  intro TEXT NULL,
  message TEXT,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
  end_date DATETIME DEFAULT NULL,
  allow_replies BIT DEFAULT 0,
  allow_rating BIT DEFAULT 0,
  rating_count INTEGER NOT NULL DEFAULT 0,
  avg_rating FLOAT DEFAULT 0,
  priority_id INTEGER DEFAULT 10,
  read_count INTEGER NOT NULL DEFAULT 0,
  enabled BIT DEFAULT 1,
  status INTEGER DEFAULT NULL,
  html BIT NOT NULL DEFAULT 1,
  start_date_timezone VARCHAR(255),
  end_date_timezone VARCHAR(255),
  classification_id INTEGER NOT NULL,
  template_id INTEGER REFERENCES lookup_news_template
);

CREATE TABLE project_requirements_map (
  map_id INT IDENTITY PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects,
  requirement_id INTEGER NOT NULL REFERENCES project_requirements,
  position INTEGER NOT NULL,
  indent INTEGER NOT NULL DEFAULT 0,
  folder_id INTEGER NULL REFERENCES project_assignments_folder,
  assignment_id INTEGER NULL REFERENCES project_assignments
);

CREATE INDEX proj_req_map_pr_req_pos_idx ON project_requirements_map (project_id, requirement_id, position);

CREATE TABLE lookup_project_permission_category (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_project_permission (
  code INT IDENTITY PRIMARY KEY,
  category_id INTEGER REFERENCES lookup_project_permission_category(code),
  permission VARCHAR(300) UNIQUE NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  group_id INTEGER NOT NULL DEFAULT 0,
  default_role INTEGER REFERENCES lookup_project_role(code)
);

CREATE TABLE project_permissions (
  id INT IDENTITY PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  permission_id INTEGER NOT NULL REFERENCES lookup_project_permission(code),
  userlevel INTEGER NOT NULL REFERENCES lookup_project_role(code)
);

CREATE TABLE project_accounts (
  id INT IDENTITY PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX proj_acct_project_idx ON project_accounts (project_id);
CREATE INDEX proj_acct_org_idx ON project_accounts (org_id);

