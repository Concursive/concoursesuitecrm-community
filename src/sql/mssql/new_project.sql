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
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);


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
);

CREATE INDEX "project_assignments_idx" ON "project_assignments"
  ("activity_id");
  
CREATE INDEX "project_assignments_cidx" ON "project_assignments" 
  ("complete_date", "user_assign_id");
  
CREATE TABLE project_assignments_status (
  status_id INT IDENTITY PRIMARY KEY,
  assignment_id INTEGER NOT NULL REFERENCES project_assignments,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  description TEXT NOT NULL,
  status_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

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
);

CREATE INDEX "project_issues_limit_idx"
  ON "project_issues"
  ("type_id", "project_id", "enteredby");
  
CREATE INDEX "project_issues_idx"
  ON "project_issues"
  ("issue_id");  
  
  
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
);

CREATE TABLE project_folders (
  folder_id INT IDENTITY PRIMARY KEY,
  link_module_id INTEGER NOT NULL,
  link_item_id INTEGER NOT NULL,
  subject VARCHAR(255) NOT NULL,
  description TEXT,
  parent INT NULL
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
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
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


CREATE TABLE project_team (
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  userLevel INTEGER NULL,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);


