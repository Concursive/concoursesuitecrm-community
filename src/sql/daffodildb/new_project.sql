
CREATE SEQUENCE lookup_project_activit_code_seq;
CREATE TABLE lookup_project_activity (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER  DEFAULT 0 NOT NULL,
  template_id INTEGER DEFAULT 0
);

CREATE SEQUENCE lookup_project_priorit_code_seq;
CREATE TABLE lookup_project_priority (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER  DEFAULT 0 NOT NULL,
  graphic VARCHAR(75),
  type INTEGER NOT NULL
);

CREATE SEQUENCE lookup_project_status_code_seq;
CREATE TABLE lookup_project_status (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER  DEFAULT 0 NOT NULL,
  graphic VARCHAR(75),
  type INTEGER NOT NULL
);

CREATE SEQUENCE lookup_project_loe_code_seq;
CREATE TABLE lookup_project_loe (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  base_value INTEGER DEFAULT 0 NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER DEFAULT 0 NOT NULL 
);

CREATE SEQUENCE lookup_project_role_code_seq;
CREATE TABLE lookup_project_role (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER  DEFAULT 0 NOT NULL
);

CREATE SEQUENCE lookup_project_cat_code_seq;
CREATE TABLE lookup_project_category (
  code INT PRIMARY KEY,
  description VARCHAR(80) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER  DEFAULT 0 NOT NULL
);

CREATE SEQUENCE lookup_news_template_code_seq;
CREATE TABLE lookup_news_template (
  code INT PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INT DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INT DEFAULT 0 NOT NULL,
  load_article boolean DEFAULT false,
  load_project_article_list boolean DEFAULT false,
  load_article_linked_list boolean DEFAULT false,
  load_public_projects boolean DEFAULT false,
  load_article_category_list boolean DEFAULT false,
  mapped_jsp VARCHAR(255) NOT NULL
);

CREATE SEQUENCE projects_project_id_seq;
CREATE TABLE projects (
  project_id INT  PRIMARY KEY,
  group_id INTEGER ,
  department_id INTEGER REFERENCES lookup_department(code),
  template_id INTEGER,
  title VARCHAR(100) NOT NULL ,
  shortDescription VARCHAR(200) NOT NULL ,
  requestedBy VARCHAR(50) ,
  requestedDept VARCHAR(50) ,
  requestDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP  ,
  approvalDate TIMESTAMP ,
  closeDate TIMESTAMP ,
  owner INTEGER ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  approvalBy INTEGER REFERENCES access(user_id),
  category_id INTEGER REFERENCES lookup_project_category(code),
  portal boolean  DEFAULT false NOT NULL,
  allow_guests boolean DEFAULT false NOT NULL,
  news_enabled boolean DEFAULT true NOT NULL,
  details_enabled boolean DEFAULT true NOT NULL,
  team_enabled boolean DEFAULT true NOT NULL,
  plan_enabled boolean DEFAULT true NOT NULL,
  lists_enabled boolean DEFAULT true NOT NULL,
  discussion_enabled boolean DEFAULT true NOT NULL,
  tickets_enabled boolean DEFAULT true NOT NULL,
  documents_enabled boolean DEFAULT true NOT NULL,
  news_label VARCHAR(50) ,
  details_label VARCHAR(50) ,
  team_label VARCHAR(50) ,
  plan_label VARCHAR(50) ,
  lists_label VARCHAR(50) ,
  discussion_label VARCHAR(50) ,
  tickets_label VARCHAR(50) ,
  documents_label VARCHAR(50) ,
  est_closedate TIMESTAMP,
  budget FLOAT,
  budget_currency VARCHAR(5),
  requestDate_timezone VARCHAR(255),
  est_closedate_timezone VARCHAR(255),
  portal_default boolean DEFAULT false NOT NULL,
  portal_header VARCHAR(255),
  portal_format VARCHAR(255),
  portal_key VARCHAR(255),
  portal_build_news_body boolean DEFAULT false NOT NULL,
  portal_news_menu boolean DEFAULT false NOT NULL,
  description CLOB,
  allows_user_observers boolean DEFAULT false NOT NULL,
  "level" INTEGER DEFAULT 10 NOT NULL,
  portal_page_type INTEGER,
  calendar_enabled boolean DEFAULT true NOT NULL,
  calendar_label VARCHAR(50),
  accounts_enabled boolean DEFAULT true NOT NULL,
  accounts_label VARCHAR(50),
  trashed_date TIMESTAMP
);

CREATE INDEX "projects_idx"   ON "projects"   ("group_id", "project_id");

CREATE SEQUENCE project_requi_requirement_i_seq;
CREATE TABLE project_requirements (
  requirement_id INT  PRIMARY KEY,
  project_id INTEGER REFERENCES projects(project_id) NOT NULL,
  submittedBy VARCHAR(50) ,
  departmentBy VARCHAR(30) ,
  shortDescription VARCHAR(255) NOT NULL,
  description CLOB NOT NULL,
  dateReceived TIMESTAMP ,
  estimated_loevalue INTEGER ,
  estimated_loetype INTEGER REFERENCES lookup_project_loe,
  actual_loevalue INTEGER ,
  actual_loetype INTEGER REFERENCES lookup_project_loe,
  deadline TIMESTAMP ,
  approvedBy INTEGER REFERENCES access(user_id),
  approvalDate TIMESTAMP ,
  closedBy INTEGER REFERENCES access(user_id),
  closeDate TIMESTAMP ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredBy INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedBy INTEGER REFERENCES access(user_id) NOT NULL ,
  startdate TIMESTAMP ,
  startdate_timezone VARCHAR(255),
  deadline_timezone VARCHAR(255),
  due_date_timezone VARCHAR(255)
);

CREATE SEQUENCE project_assignmen_folder_id_seq;
CREATE TABLE project_assignments_folder (
  folder_id INT  PRIMARY KEY,
  parent_id INTEGER REFERENCES project_assignments_folder(folder_id),
  requirement_id INTEGER  REFERENCES project_requirements(requirement_id) NOT NULL,
  name VARCHAR(255) NOT NULL,
  description CLOB ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredBy INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedBy INTEGER REFERENCES access(user_id) NOT NULL 
);

CREATE SEQUENCE project_assig_assignment_id_seq;
CREATE TABLE project_assignments (
  assignment_id INT  PRIMARY KEY,
  project_id INTEGER REFERENCES projects(project_id) NOT NULL ,
  requirement_id INTEGER REFERENCES project_requirements(requirement_id),
  assignedBy INTEGER REFERENCES access(user_id),
  user_assign_id INTEGER REFERENCES access(user_id),
  technology VARCHAR(50) ,
  "role" VARCHAR(255) ,
  estimated_loevalue INTEGER ,
  estimated_loetype INTEGER REFERENCES lookup_project_loe,
  actual_loevalue INTEGER ,
  actual_loetype INTEGER REFERENCES lookup_project_loe,
  priority_id INTEGER REFERENCES lookup_project_priority,
  assign_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  est_start_date TIMESTAMP ,
  start_date TIMESTAMP ,
  due_date TIMESTAMP ,
  status_id INTEGER REFERENCES lookup_project_status,
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  complete_date TIMESTAMP ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredBy INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedBy INTEGER REFERENCES access(user_id) NOT NULL ,
  folder_id INTEGER REFERENCES project_assignments_folder(folder_id),
  percent_complete INTEGER ,
  due_date_timezone VARCHAR(255) 
);

CREATE INDEX "project_assignments_cidx" ON "project_assignments"
  ("complete_date", "user_assign_id");

CREATE INDEX proj_assign_req_id_idx ON project_assignments (requirement_id);

CREATE SEQUENCE project_assignmen_status_id_seq;
CREATE TABLE project_assignments_status (
  status_id INT PRIMARY KEY,
  assignment_id INTEGER NOT NULL REFERENCES project_assignments,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  description CLOB NOT NULL,
  status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  percent_complete INTEGER,
  project_status_id INTEGER REFERENCES lookup_project_status,
  user_assign_id INTEGER REFERENCES access(user_id)
);


CREATE SEQUENCE project_issue_cate_categ_id_seq;
CREATE TABLE project_issues_categories (
  category_id INT  PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  subject VARCHAR(255) NOT NULL,
  description CLOB ,
  enabled boolean DEFAULT true NOT NULL ,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredBy INTEGER  REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedBy INTEGER REFERENCES access(user_id) NOT NULL ,
  topics_count INTEGER  DEFAULT 0,
  posts_count INTEGER DEFAULT 0,
  last_post_date TIMESTAMP,
  last_post_by INTEGER,
  allow_files boolean DEFAULT false NOT NULL
);

CREATE SEQUENCE project_issues_issue_id_seq;
CREATE TABLE project_issues (
  issue_id INT  PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  subject VARCHAR(255) NOT NULL,
  message CLOB NOT NULL,
  importance INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredBy INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedBy INTEGER REFERENCES access(user_id) NOT NULL ,
  category_id INTEGER  REFERENCES project_issues_categories(category_id),
  reply_count INTEGER DEFAULT 0 NOT NULL ,
  last_reply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  last_reply_by INTEGER
);

CREATE SEQUENCE project_issue_repl_reply_id_seq;
CREATE TABLE project_issue_replies (
  reply_id INT  PRIMARY KEY ,
  issue_id INTEGER NOT NULL REFERENCES project_issues,
  reply_to INTEGER DEFAULT 0 ,
  subject VARCHAR(255) NOT NULL ,
  message CLOB NOT NULL ,
  importance INTEGER ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredBy INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedBy INTEGER REFERENCES access(user_id) NOT NULL 
);

CREATE SEQUENCE project_folders_folder_id_seq;
CREATE TABLE project_folders (
  folder_id INT   PRIMARY KEY,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  subject VARCHAR(255) NOT NULL,
  description CLOB,
  parent_id INT ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredBy INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedBy INTEGER  REFERENCES access(user_id) NOT NULL ,
  display INTEGER 
);

CREATE SEQUENCE project_files_item_id_seq;
CREATE TABLE project_files (
  item_id INT  PRIMARY KEY ,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  folder_id INTEGER  REFERENCES project_folders,
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
  subject VARCHAR(500) NOT NULL ,
  "size" INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 , 
  enabled boolean DEFAULT true ,
  downloads INTEGER DEFAULT 0,
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredBy INTEGER  REFERENCES access(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedBy INTEGER  REFERENCES access(user_id)  NOT NULL,
  default_file boolean DEFAULT false,
  allow_portal_access BOOLEAN DEFAULT false
);

CREATE INDEX "project_files_cidx" ON "project_files"   ("link_module_id", "link_item_id");

CREATE TABLE project_files_version (
  item_id INTEGER REFERENCES project_files(item_id),
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
  subject VARCHAR(500) NOT NULL ,
  "size" INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 ,
  enabled boolean DEFAULT true ,
  downloads INTEGER DEFAULT 0,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredBy INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedBy INTEGER REFERENCES access(user_id) NOT NULL,
  allow_portal_access BOOLEAN DEFAULT false
);

CREATE TABLE project_files_download (
  item_id INTEGER NOT NULL REFERENCES project_files(item_id),
  version FLOAT DEFAULT 0 ,
  user_download_id INTEGER REFERENCES access(user_id),
  download_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL 
);

CREATE TABLE project_files_thumbnail (
  item_id INTEGER REFERENCES project_files(item_id),
  filename VARCHAR(255) NOT NULL,
  "size" INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 ,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredBy INTEGER REFERENCES access(user_id) NOT NULL ,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  modifiedBy INTEGER REFERENCES access(user_id) NOT NULL 
);


CREATE TABLE project_team (
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  userlevel INTEGER NOT NULL REFERENCES lookup_project_role(code),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  status INTEGER ,
  last_accessed TIMESTAMP,
  role_type INTEGER
);

CREATE INDEX project_team_uni_idx ON project_team (project_id, user_id);

CREATE SEQUENCE project_news_category_category_id_seq;
CREATE TABLE project_news_category (
  category_id INT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  category_name VARCHAR(255),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  "level" INTEGER DEFAULT 0 NOT NULL,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE project_news_news_id_seq;
CREATE TABLE project_news (
  news_id INT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  category_id INTEGER REFERENCES project_news_category(category_id),
  subject VARCHAR(255) NOT NULL,
  intro CLOB,
  message CLOB,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP ,
  allow_replies boolean DEFAULT false,
  allow_rating boolean DEFAULT false,
  rating_count INTEGER  DEFAULT 0 NOT NULL,
  avg_rating FLOAT DEFAULT 0,
  priority_id INTEGER DEFAULT 10,
  read_count INTEGER DEFAULT 0 NOT NULL,
  enabled boolean DEFAULT true,
  status INTEGER  ,
  html boolean DEFAULT true  NOT NULL,
  start_date_timezone VARCHAR(255),
  end_date_timezone VARCHAR(255),
  classification_id INTEGER NOT NULL,
  template_id INTEGER REFERENCES lookup_news_template
);

CREATE SEQUENCE project_requirements_map_map_id_seq;
CREATE TABLE project_requirements_map (
  map_id INT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects,
  requirement_id INTEGER NOT NULL REFERENCES project_requirements,
  position INTEGER NOT NULL,
  indent INTEGER DEFAULT 0 NOT NULL ,
  folder_id INTEGER REFERENCES project_assignments_folder,
  assignment_id INTEGER REFERENCES project_assignments
);

CREATE INDEX proj_req_map_pr_req_pos_idx ON project_requirements_map (project_id, requirement_id, position);

CREATE SEQUENCE lookup_project_permission_category_code_seq;
CREATE TABLE lookup_project_permission_category (
  code INT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER DEFAULT 0 NOT NULL
);

CREATE SEQUENCE lookup_project_permission_code_seq;
CREATE TABLE lookup_project_permission (
  code INT PRIMARY KEY,
  category_id INTEGER REFERENCES lookup_project_permission_category(code),
  permission VARCHAR(300) UNIQUE NOT NULL,
  description VARCHAR(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true,
  group_id INTEGER  DEFAULT 0 NOT NULL,
  default_role INTEGER REFERENCES lookup_project_role(code)
);

CREATE SEQUENCE project_permissions_id_seq;
CREATE TABLE project_permissions (
  id INT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  permission_id INTEGER NOT NULL REFERENCES lookup_project_permission(code),
  userlevel INTEGER NOT NULL REFERENCES lookup_project_role(code)
);

CREATE SEQUENCE project_accounts_id_seq;
CREATE TABLE project_accounts (
  id INT PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX proj_acct_project_idx ON project_accounts (project_id);
CREATE INDEX proj_acct_org_idx ON project_accounts (org_id);

