/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created
 *@version    $Id$
 */
 
CREATE SEQUENCE lookup_project_activit_code_seq;
CREATE TABLE lookup_project_activity (
  code INTEGER DEFAULT nextval('lookup_project_activit_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  template_id INTEGER DEFAULT 0
);

CREATE SEQUENCE lookup_project_priorit_code_seq;
CREATE TABLE lookup_project_priority (
  code INTEGER DEFAULT nextval('lookup_project_priorit_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0,
  graphic VARCHAR(75),
  type INTEGER NOT NULL
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

CREATE TABLE lookup_project_role (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE SEQUENCE lookup_project_cat_code_seq;
CREATE TABLE lookup_project_category (
  code INTEGER DEFAULT nextval('lookup_project_cat_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(80) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);


CREATE TABLE projects (
  project_id SERIAL PRIMARY KEY,
  group_id INTEGER NULL,
  department_id INTEGER REFERENCES lookup_department(code),
  template_id INTEGER,
  title VARCHAR(100) NOT NULL ,
  shortDescription VARCHAR(200) NOT NULL ,
  requestedBy VARCHAR(50) NULL ,
  requestedDept VARCHAR(50) NULL ,
  requestDate TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP NULL ,
  approvalDate TIMESTAMP(3) NULL,
  approvalBy INTEGER NULL REFERENCES access(user_id),
  closeDate TIMESTAMP(3) NULL,
  owner INTEGER NULL,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
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
  est_closedate TIMESTAMP(3),
  budget FLOAT,
  budget_currency VARCHAR(5)
);

CREATE INDEX "projects_idx"
  ON "projects"
  USING btree ("group_id", "project_id");

  
CREATE SEQUENCE project_requi_requirement_i_seq;
CREATE TABLE project_requirements (
  requirement_id INTEGER DEFAULT nextval('project_requi_requirement_i_seq') NOT NULL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  submittedBy VARCHAR(50) NULL,
  departmentBy VARCHAR(30) NULL,
  shortDescription VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  dateReceived TIMESTAMP(3) NULL,
  estimated_loevalue INTEGER NULL,
  estimated_loetype INTEGER REFERENCES lookup_project_loe,
  actual_loevalue INTEGER NULL,
  actual_loetype INTEGER REFERENCES lookup_project_loe,
  deadline TIMESTAMP(3) NULL,
  approvedBy INTEGER REFERENCES access(user_id),
  approvalDate TIMESTAMP(3) NULL,
  closedBy INTEGER REFERENCES access(user_id),
  closeDate TIMESTAMP(3) NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  startdate TIMESTAMP(3) NULL
);

CREATE SEQUENCE project_assignmen_folder_id_seq;
CREATE TABLE project_assignments_folder (
  folder_id INTEGER DEFAULT nextval('project_assignmen_folder_id_seq') NOT NULL PRIMARY KEY,
  parent_id INTEGER NULL REFERENCES project_assignments_folder(folder_id),
  requirement_id INTEGER NOT NULL REFERENCES project_requirements(requirement_id),
  name VARCHAR(255) NOT NULL,
  description TEXT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);

CREATE SEQUENCE project_assig_assignment_id_seq;
CREATE TABLE project_assignments (
  assignment_id INTEGER DEFAULT nextval('project_assig_assignment_id_seq') NOT NULL PRIMARY KEY,
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
  assign_date TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  est_start_date TIMESTAMP(3) NULL,
  start_date TIMESTAMP(3) NULL,
  due_date TIMESTAMP(3) NULL,
  status_id INTEGER REFERENCES lookup_project_status,
  status_date TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP NOT NULL,
  complete_date TIMESTAMP(3) NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  folder_id INTEGER NULL REFERENCES project_assignments_folder(folder_id),
  percent_complete INTEGER NULL
);
  
CREATE INDEX "project_assignments_cidx" ON "project_assignments" 
  USING btree ("complete_date", "user_assign_id");
  
CREATE SEQUENCE project_assignmen_status_id_seq;
CREATE TABLE project_assignments_status (
  status_id INTEGER DEFAULT nextval('project_assignmen_status_id_seq') NOT NULL PRIMARY KEY,
  assignment_id INTEGER NOT NULL REFERENCES project_assignments,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  description TEXT NOT NULL,
  status_date TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);


CREATE SEQUENCE project_issue_cate_categ_id_seq;
CREATE TABLE project_issues_categories (
  category_id INTEGER DEFAULT nextval('project_issue_cate_categ_id_seq') NOT NULL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  subject VARCHAR(255) NOT NULL,
  description TEXT NULL,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  topics_count INTEGER NOT NULL DEFAULT 0,
  posts_count INTEGER NOT NULL DEFAULT 0,
  last_post_date TIMESTAMP(3),
  last_post_by INTEGER
);

CREATE TABLE project_issues (
  issue_id SERIAL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  category_id INTEGER NULL REFERENCES project_issues_categories(category_id),
  subject VARCHAR(255) NOT NULL,
  message TEXT NOT NULL,
  importance INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id),
  reply_count INTEGER NOT NULL DEFAULT 0,
  last_reply_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_reply_by INTEGER
);  
  
CREATE SEQUENCE project_issue_repl_reply_id_seq;
CREATE TABLE project_issue_replies (
  reply_id INTEGER DEFAULT nextval('project_issue_repl_reply_id_seq') NOT NULL PRIMARY KEY,
  issue_id INTEGER NOT NULL REFERENCES project_issues,
  reply_to INTEGER DEFAULT 0 ,
  subject VARCHAR(50) NOT NULL ,
  message TEXT NOT NULL ,
  importance INTEGER NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);

CREATE TABLE project_folders (
  folder_id SERIAL PRIMARY KEY,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  subject VARCHAR(255) NOT NULL,
  description TEXT,
  parent_id INT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  display INTEGER NULL
);
  
CREATE TABLE project_files (
  item_id SERIAL PRIMARY KEY ,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  folder_id INTEGER NULL REFERENCES project_folders,
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
  subject VARCHAR(500) NOT NULL ,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 , 
  enabled BOOLEAN DEFAULT TRUE ,
  downloads INTEGER DEFAULT 0,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);

CREATE INDEX "project_files_cidx" ON "project_files" 
  USING btree ("link_module_id", "link_item_id");

CREATE TABLE project_files_version (
  item_id INTEGER REFERENCES project_files(item_id),
  client_filename VARCHAR(255) NOT NULL,
  filename VARCHAR(255) NOT NULL,
  subject VARCHAR(500) NOT NULL ,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 ,
  enabled BOOLEAN DEFAULT TRUE ,
  downloads INTEGER DEFAULT 0,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);

CREATE TABLE project_files_download (
  item_id INTEGER NOT NULL REFERENCES project_files(item_id),
  version FLOAT DEFAULT 0 ,
  user_download_id INTEGER NULL REFERENCES access(user_id),
  download_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE project_files_thumbnail (
  item_id INTEGER REFERENCES project_files(item_id),
  filename VARCHAR(255) NOT NULL,
  size INTEGER DEFAULT 0 ,
  version FLOAT DEFAULT 0 ,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);


CREATE TABLE project_team (
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  userlevel INTEGER NOT NULL REFERENCES lookup_project_role(code),
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  status INTEGER NULL,
  last_accessed TIMESTAMP(3)
);
CREATE UNIQUE INDEX project_team_uni_idx ON project_team (project_id, user_id);

CREATE TABLE project_news (
  news_id SERIAL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  category_id INTEGER NULL,
  subject VARCHAR(255) NOT NULL,
  intro VARCHAR(2048) NULL,
  message TEXT,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
   modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  end_date TIMESTAMP DEFAULT NULL,
  allow_replies BOOLEAN DEFAULT false,
  allow_rating BOOLEAN DEFAULT false,
  rating_count INTEGER NOT NULL DEFAULT 0,
  avg_rating FLOAT DEFAULT 0,
  priority_id INTEGER DEFAULT 10,
  read_count INTEGER NOT NULL DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  status INTEGER DEFAULT NULL,
  html BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE project_requirements_map (
  map_id SERIAL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects,
  requirement_id INTEGER NOT NULL REFERENCES project_requirements,
  position INTEGER NOT NULL,
  indent INTEGER NOT NULL DEFAULT 0,
  folder_id INTEGER NULL REFERENCES project_assignments_folder,
  assignment_id INTEGER NULL REFERENCES project_assignments
);

CREATE INDEX proj_req_map_pr_req_pos_idx ON project_requirements_map (project_id, requirement_id, position);

CREATE TABLE lookup_project_permission_category (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  group_id INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE lookup_project_permission (
  code SERIAL PRIMARY KEY,
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
  id SERIAL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  permission_id INTEGER NOT NULL REFERENCES lookup_project_permission(code),
  userlevel INTEGER NOT NULL REFERENCES lookup_project_role(code)
);
