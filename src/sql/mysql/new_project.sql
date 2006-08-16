-- ----------------------------------------------------------------------------
--  PostgreSQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------
 
CREATE TABLE lookup_project_activity (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  template_id INTEGER DEFAULT 0
);

CREATE TABLE lookup_project_priority (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  graphic VARCHAR(75),
  type INTEGER NOT NULL
);

CREATE TABLE lookup_project_status (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  graphic VARCHAR(75),
  type INTEGER NOT NULL
);

CREATE TABLE lookup_project_loe (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  base_value INTEGER DEFAULT 0 NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_project_role (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_project_category (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(80) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_news_template (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  load_article BOOLEAN DEFAULT false,
  load_project_article_list BOOLEAN DEFAULT false,
  load_article_linked_list BOOLEAN DEFAULT false,
  load_public_projects BOOLEAN DEFAULT false,
  load_article_category_list BOOLEAN DEFAULT false,
  mapped_jsp VARCHAR(255) NOT NULL
);


CREATE TABLE projects (
  project_id INT AUTO_INCREMENT PRIMARY KEY,
  group_id INTEGER NULL,
  department_id INTEGER REFERENCES lookup_department(code),
  template_id INTEGER,
  title VARCHAR(100) NOT NULL ,
  shortDescription VARCHAR(200) NOT NULL ,
  requestedBy VARCHAR(50) NULL ,
  requestedDept VARCHAR(50) NULL ,
  requestDate TIMESTAMP NULL,
  approvalDate TIMESTAMP NULL,
  closeDate TIMESTAMP NULL,
  owner INTEGER NULL,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id),
  approvalBy INTEGER NULL REFERENCES `access`(user_id),
  category_id INTEGER NULL REFERENCES lookup_project_category(code),
  portal BOOLEAN NOT NULL DEFAULT false,
  allow_guests BOOLEAN NOT NULL DEFAULT false,
  news_enabled BOOLEAN NOT NULL DEFAULT true,
  details_enabled BOOLEAN NOT NULL DEFAULT true,
  team_enabled BOOLEAN NOT NULL DEFAULT true,
  plan_enabled BOOLEAN NOT NULL DEFAULT true,
  lists_enabled BOOLEAN NOT NULL DEFAULT true,
  discussion_enabled BOOLEAN NOT NULL DEFAULT true,
  tickets_enabled BOOLEAN NOT NULL DEFAULT true,
  documents_enabled BOOLEAN NOT NULL DEFAULT true,
  news_label VARCHAR(50) NULL,
  details_label VARCHAR(50) NULL,
  team_label VARCHAR(50) NULL,
  plan_label VARCHAR(50) NULL,
  lists_label VARCHAR(50) NULL,
  discussion_label VARCHAR(50) NULL,
  tickets_label VARCHAR(50) NULL,
  documents_label VARCHAR(50) NULL,
  est_closedate TIMESTAMP NULL,
  budget FLOAT,
  budget_currency VARCHAR(5),
  requestDate_timezone VARCHAR(255),
  est_closedate_timezone VARCHAR(255),
  portal_default BOOLEAN NOT NULL DEFAULT false,
  portal_header VARCHAR(255),
  portal_format VARCHAR(255),
  portal_key VARCHAR(255),
  portal_build_news_body BOOLEAN NOT NULL DEFAULT false,
  portal_news_menu BOOLEAN NOT NULL DEFAULT false,
  description TEXT,
  allows_user_observers BOOLEAN NOT NULL DEFAULT false,
  level INTEGER NOT NULL DEFAULT 10,
  portal_page_type INTEGER,
  calendar_enabled BOOLEAN NOT NULL DEFAULT true,
  calendar_label VARCHAR(50) NULL,
  accounts_enabled BOOLEAN NOT NULL DEFAULT true,
  accounts_label VARCHAR(50) NULL,
  trashed_date TIMESTAMP NULL
);

CREATE INDEX `projects_idx` USING btree ON `projects` (`group_id`, `project_id`);

  
CREATE TABLE project_requirements (
  requirement_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  submittedBy VARCHAR(50) ,
  departmentBy VARCHAR(30) ,
  shortDescription VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  dateReceived TIMESTAMP NULL,
  estimated_loevalue INTEGER ,
  estimated_loetype INTEGER REFERENCES lookup_project_loe,
  actual_loevalue INTEGER ,
  actual_loetype INTEGER REFERENCES lookup_project_loe,
  deadline TIMESTAMP NULL,
  approvedBy INTEGER REFERENCES `access`(user_id),
  approvalDate TIMESTAMP NULL,
  closedBy INTEGER REFERENCES `access`(user_id),
  closeDate TIMESTAMP NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id),
  startdate TIMESTAMP NULL,
  startdate_timezone VARCHAR(255),
  deadline_timezone VARCHAR(255),
  due_date_timezone VARCHAR(255)
);

CREATE TABLE project_assignments_folder (
  folder_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  parent_id INTEGER NULL REFERENCES project_assignments_folder(folder_id),
  requirement_id INTEGER NOT NULL REFERENCES project_requirements(requirement_id),
  name VARCHAR(255) NOT NULL,
  description TEXT ,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id)
);

CREATE TABLE project_assignments (
  assignment_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  requirement_id INTEGER NULL REFERENCES project_requirements(requirement_id),
  assignedBy INTEGER REFERENCES `access`(user_id),
  user_assign_id INTEGER NULL REFERENCES `access`(user_id),
  technology VARCHAR(50) ,
  role VARCHAR(255) ,
  estimated_loevalue INTEGER ,
  estimated_loetype INTEGER REFERENCES lookup_project_loe,
  actual_loevalue INTEGER ,
  actual_loetype INTEGER REFERENCES lookup_project_loe,
  priority_id INTEGER REFERENCES lookup_project_priority,
  assign_date TIMESTAMP NULL,
  est_start_date TIMESTAMP NULL,
  start_date TIMESTAMP NULL,
  due_date TIMESTAMP NULL,
  status_id INTEGER REFERENCES lookup_project_status,
  status_date TIMESTAMP NULL,
  complete_date TIMESTAMP NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id),
  folder_id INTEGER REFERENCES project_assignments_folder(folder_id),
  percent_complete INTEGER ,
  due_date_timezone VARCHAR(255)
);
  
CREATE INDEX `project_assignments_cidx` USING btree ON `project_assignments` (`complete_date`, `user_assign_id`);
  
CREATE INDEX proj_assign_req_id_idx ON project_assignments (requirement_id);
  
CREATE TABLE project_assignments_status (
  status_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  assignment_id INTEGER NOT NULL REFERENCES project_assignments,
  user_id INTEGER NOT NULL REFERENCES `access`(user_id),
  description TEXT NOT NULL,
  status_date TIMESTAMP NULL,
  percent_complete INTEGER ,
  project_status_id INTEGER REFERENCES lookup_project_status,
  user_assign_id INTEGER  REFERENCES `access`(user_id)
);


CREATE TABLE project_issues_categories (
  category_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  subject VARCHAR(255) NOT NULL,
  description TEXT NULL,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id),
  topics_count INTEGER NOT NULL DEFAULT 0,
  posts_count INTEGER NOT NULL DEFAULT 0,
  last_post_date TIMESTAMP NULL,
  last_post_by INTEGER,
  allow_files BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE project_issues (
  issue_id INT AUTO_INCREMENT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  subject VARCHAR(255) NOT NULL,
  message TEXT NOT NULL,
  importance INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id),
  category_id INTEGER NULL REFERENCES project_issues_categories(category_id),
  reply_count INTEGER NOT NULL DEFAULT 0,
  last_reply_date TIMESTAMP NULL,
  last_reply_by INTEGER
);  
  
CREATE TABLE project_issue_replies (
  reply_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  issue_id INTEGER NOT NULL REFERENCES project_issues,
  reply_to INTEGER DEFAULT 0 ,
  subject VARCHAR(255) NOT NULL ,
  message TEXT NOT NULL ,
  importance INTEGER NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id)
);

CREATE TABLE project_folders (
  folder_id INT AUTO_INCREMENT PRIMARY KEY,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  subject VARCHAR(255) NOT NULL,
  description TEXT,
  parent_id INT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id),
  display INTEGER NULL
);
  
CREATE TABLE project_files (
  item_id INT AUTO_INCREMENT PRIMARY KEY ,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  folder_id INTEGER REFERENCES project_folders,
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
  subject VARCHAR(500) NOT NULL ,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 , 
  enabled BOOLEAN DEFAULT true ,
  downloads INTEGER DEFAULT 0,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id),
  default_file BOOLEAN DEFAULT false,
  allow_portal_access BOOLEAN DEFAULT false
);

CREATE INDEX `project_files_cidx` USING btree ON `project_files` (`link_module_id`, `link_item_id`);

CREATE TABLE project_files_version (
  item_id INTEGER REFERENCES project_files(item_id),
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
  subject VARCHAR(500) NOT NULL ,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 ,
  enabled BOOLEAN DEFAULT true ,
  downloads INTEGER DEFAULT 0,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id),
  allow_portal_access BOOLEAN DEFAULT false
);

CREATE TABLE project_files_download (
  item_id INTEGER NOT NULL REFERENCES project_files(item_id),
  version FLOAT DEFAULT 0 ,
  user_download_id INTEGER NULL REFERENCES `access`(user_id),
  download_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE project_files_thumbnail (
  item_id INTEGER REFERENCES project_files(item_id),
  filename VARCHAR(255) NOT NULL,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 ,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES `access`(user_id)
);


CREATE TABLE project_team (
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  user_id INTEGER NOT NULL REFERENCES `access`(user_id),
  userlevel INTEGER NOT NULL REFERENCES lookup_project_role(code),
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INTEGER NOT NULL REFERENCES `access`(user_id),
  status INTEGER NULL,
  last_accessed TIMESTAMP NULL,
  role_type INTEGER
);
CREATE UNIQUE INDEX project_team_uni_idx ON project_team (project_id, user_id);

CREATE TABLE project_news_category (
  category_id INT AUTO_INCREMENT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  category_name VARCHAR(255),
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  level INTEGER NOT NULL DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE project_news (
  news_id INT AUTO_INCREMENT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  category_id INTEGER NULL REFERENCES project_news_category(category_id),
  subject VARCHAR(255) NOT NULL,
  intro TEXT NULL,
  message TEXT,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INTEGER NOT NULL REFERENCES `access`(user_id),
  start_date TIMESTAMP NULL,
  end_date TIMESTAMP NULL,
  allow_replies BOOLEAN DEFAULT false,
  allow_rating BOOLEAN DEFAULT false,
  rating_count INTEGER NOT NULL DEFAULT 0,
  avg_rating FLOAT DEFAULT 0,
  priority_id INTEGER DEFAULT 10,
  read_count INTEGER NOT NULL DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  status INTEGER DEFAULT NULL,
  html BOOLEAN NOT NULL DEFAULT true,
  start_date_timezone VARCHAR(255),
  end_date_timezone VARCHAR(255),
  classification_id INTEGER NOT NULL,
  template_id INTEGER REFERENCES lookup_news_template
);

CREATE TABLE project_requirements_map (
  map_id INT AUTO_INCREMENT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects,
  requirement_id INTEGER NOT NULL REFERENCES project_requirements,
  position INTEGER NOT NULL,
  indent INTEGER NOT NULL DEFAULT 0,
  folder_id INTEGER NULL REFERENCES project_assignments_folder,
  assignment_id INTEGER NULL REFERENCES project_assignments
);

CREATE INDEX proj_req_map_pr_req_pos_idx ON project_requirements_map (project_id, requirement_id, position);

CREATE TABLE lookup_project_permission_category (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_project_permission (
  code INT AUTO_INCREMENT PRIMARY KEY,
  category_id INTEGER REFERENCES lookup_project_permission_category(code),
  permission VARCHAR(300) UNIQUE NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  default_role INTEGER REFERENCES lookup_project_role(code)
);

CREATE TABLE project_permissions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  permission_id INTEGER NOT NULL REFERENCES lookup_project_permission(code),
  userlevel INTEGER NOT NULL REFERENCES lookup_project_role(code)
);

CREATE TABLE project_accounts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX proj_acct_project_idx ON project_accounts (project_id);
CREATE INDEX proj_acct_org_idx ON project_accounts (org_id);

