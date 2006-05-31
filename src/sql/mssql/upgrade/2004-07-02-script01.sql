-- PROJECTS

DROP INDEX project_issues.project_issues_limit_idx;
DROP INDEX project_issues.project_issues_idx;

-- TODO: rewrite this with a generic drop constraint
ALTER TABLE project_issues DROP CONSTRAINT FK__project_i__type___2B2A60FE;
ALTER TABLE project_issues DROP COLUMN type_id;
DROP TABLE lookup_project_issues;

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

ALTER TABLE projects ADD approvalby INTEGER NULL REFERENCES access(user_id);
ALTER TABLE projects ADD category_id INTEGER NULL REFERENCES lookup_project_category(code);

ALTER TABLE projects ADD portal BIT DEFAULT 0;
UPDATE projects SET portal = 0 WHERE portal IS NULL;
ALTER TABLE projects ALTER COLUMN portal BIT NOT NULL;

ALTER TABLE projects ADD allow_guests BIT DEFAULT 0;
UPDATE projects SET allow_guests = 0 WHERE allow_guests IS NULL;
ALTER TABLE projects ALTER COLUMN allow_guests BIT NOT NULL;

ALTER TABLE projects ADD news_enabled BIT DEFAULT 1;
UPDATE projects SET news_enabled = 1 WHERE news_enabled IS NULL;
ALTER TABLE projects ALTER COLUMN news_enabled BIT NOT NULL;

ALTER TABLE projects ADD details_enabled BIT DEFAULT 1;
UPDATE projects SET details_enabled = 1 WHERE details_enabled IS NULL;
ALTER TABLE projects ALTER COLUMN details_enabled BIT NOT NULL;

ALTER TABLE projects ADD team_enabled BIT DEFAULT 1;
UPDATE projects SET team_enabled = 1 WHERE team_enabled IS NULL;
ALTER TABLE projects ALTER COLUMN team_enabled BIT NOT NULL;

ALTER TABLE projects ADD plan_enabled BIT DEFAULT 1;
UPDATE projects SET plan_enabled = 1 WHERE plan_enabled IS NULL;
ALTER TABLE projects ALTER COLUMN plan_enabled BIT NOT NULL;

ALTER TABLE projects ADD lists_enabled BIT DEFAULT 1;
UPDATE projects SET lists_enabled = 1 WHERE lists_enabled IS NULL;
ALTER TABLE projects ALTER COLUMN lists_enabled BIT NOT NULL;

ALTER TABLE projects ADD discussion_enabled BIT DEFAULT 1;
UPDATE projects SET discussion_enabled = 1 WHERE discussion_enabled IS NULL;
ALTER TABLE projects ALTER COLUMN discussion_enabled BIT NOT NULL;

ALTER TABLE projects ADD tickets_enabled BIT DEFAULT 1;
UPDATE projects SET tickets_enabled = 1 WHERE tickets_enabled IS NULL;
ALTER TABLE projects ALTER COLUMN tickets_enabled BIT NOT NULL;

ALTER TABLE projects ADD documents_enabled BIT DEFAULT 1;
UPDATE projects SET documents_enabled = 1 WHERE documents_enabled IS NULL;
ALTER TABLE projects ALTER COLUMN documents_enabled BIT NOT NULL;

ALTER TABLE projects ADD news_label VARCHAR(50) NULL;
ALTER TABLE projects ADD details_label VARCHAR(50) NULL;
ALTER TABLE projects ADD team_label VARCHAR(50) NULL;
ALTER TABLE projects ADD plan_label VARCHAR(50) NULL;
ALTER TABLE projects ADD lists_label VARCHAR(50) NULL;
ALTER TABLE projects ADD discussion_label VARCHAR(50) NULL;
ALTER TABLE projects ADD tickets_label VARCHAR(50) NULL;
ALTER TABLE projects ADD documents_label VARCHAR(50) NULL;
ALTER TABLE projects ADD est_closedate DATETIME;
ALTER TABLE projects ADD budget FLOAT;
ALTER TABLE projects ADD budget_currency VARCHAR(5);


ALTER TABLE project_requirements ADD startdate DATETIME NULL;

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

DROP INDEX project_assignments.project_assignments_idx;

-- TODO: replace with generic drop constraint
ALTER TABLE project_assignments DROP CONSTRAINT FK__project_a__activ__190BB0C3;
ALTER TABLE project_assignments DROP COLUMN activity_id;
ALTER TABLE project_assignments ADD folder_id INTEGER NULL REFERENCES project_assignments_folder(folder_id);
ALTER TABLE project_assignments ADD percent_complete INTEGER NULL;

--DROP INDEX project_assignments_idx;

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
  last_post_by INTEGER
);

ALTER TABLE project_issues ADD category_id INTEGER NULL REFERENCES project_issues_categories(category_id);

ALTER TABLE project_issues ADD reply_count INTEGER DEFAULT 0;
UPDATE project_issues SET reply_count = 0 WHERE reply_count IS NULL;
ALTER TABLE project_issues ALTER COLUMN reply_count INTEGER NOT NULL;

ALTER TABLE project_issues ADD last_reply_date DATETIME DEFAULT CURRENT_TIMESTAMP;
UPDATE project_issues SET last_reply_date = modified WHERE last_reply_date IS NULL;
ALTER TABLE project_issues ALTER COLUMN last_reply_date DATETIME NOT NULL;

ALTER TABLE project_issues ADD last_reply_by INTEGER;

ALTER TABLE project_folders ADD parent_id INTEGER NULL;

ALTER TABLE project_folders ADD entered DATETIME DEFAULT CURRENT_TIMESTAMP;
UPDATE project_folders SET entered = CURRENT_TIMESTAMP WHERE entered IS NULL;
ALTER TABLE project_folders ALTER COLUMN entered DATETIME NOT NULL;

ALTER TABLE project_folders ADD enteredBy INTEGER REFERENCES access(user_id);
UPDATE project_folders SET enteredBy = 0 WHERE enteredBy IS NULL;
ALTER TABLE project_folders ALTER COLUMN enteredBy INTEGER NOT NULL;

ALTER TABLE project_folders ADD modified DATETIME DEFAULT CURRENT_TIMESTAMP;
UPDATE project_folders SET modified = CURRENT_TIMESTAMP WHERE modified IS NULL;
ALTER TABLE project_folders ALTER COLUMN modified DATETIME NOT NULL;

ALTER TABLE project_folders ADD modifiedBy INTEGER REFERENCES access(user_id);
UPDATE project_folders SET modifiedBy = 0 WHERE modifiedBy IS NULL;
ALTER TABLE project_folders ALTER COLUMN modifiedBy INTEGER NOT NULL;

ALTER TABLE project_folders ADD display INTEGER NULL;

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

-- ADD REFERENCE userlevel INTEGER NOT NULL REFERENCES lookup_project_role(code),
ALTER TABLE project_team ADD status INTEGER NULL;
ALTER TABLE project_team ADD last_accessed DATETIME;

CREATE UNIQUE INDEX project_team_uni_idx ON project_team (project_id, user_id);

CREATE TABLE project_news (
  news_id INT IDENTITY PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  category_id INTEGER NULL,
  subject VARCHAR(255) NOT NULL,
  intro VARCHAR(2048) NULL,
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
  html BIT NOT NULL DEFAULT 1
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

-- TASKS
ALTER TABLE tasklink_contact ADD notes TEXT;


-- TMS
CREATE TABLE ticketlink_project (
  ticket_id INT NOT NULL REFERENCES ticket(ticketid),
  project_id INT NOT NULL REFERENCES projects(project_id)
);

CREATE TABLE project_ticket_count (
  project_id INT UNIQUE NOT NULL REFERENCES projects(project_id),
  key_count INT NOT NULL DEFAULT 0
);

ALTER TABLE ticket ADD key_count INT;

-- DEFAULT DATA

INSERT INTO lookup_project_role (group_id, level, description) VALUES (1, 10, 'Project Lead');
INSERT INTO lookup_project_role (group_id, level, description) VALUES (1, 20, 'Contributor');
INSERT INTO lookup_project_role (group_id, level, description) VALUES (1, 30, 'Observer');
INSERT INTO lookup_project_role (group_id, level, description) VALUES (1, 100, 'Guest');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 10, 'Project Details');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 1, 10, 4, 'project-details-view', 'View project details');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 1, 20, 1, 'project-details-edit', 'Modify project details');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 1, 30, 1, 'project-details-delete', 'Delete project');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 20, 'Team Members');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 2, 10, 4, 'project-team-view', 'View team members');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 2, 20, 3, 'project-team-view-email', 'See team member email addresses');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 2, 30, 1, 'project-team-edit', 'Modify team');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 2, 40, 1, 'project-team-edit-role', 'Modify team member role');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 30, 'News');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 10, 4, 'project-news-view', 'View current news');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 20, 2, 'project-news-view-unreleased', 'View unreleased news');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 30, 3, 'project-news-view-archived', 'View archived news');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 40, 2, 'project-news-add', 'Add news');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 50, 2, 'project-news-edit', 'Edit news');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 3, 60, 1, 'project-news-delete', 'Delete news');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 40, 'Plan/Outlines');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 10, 4, 'project-plan-view', 'View outlines');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 20, 1, 'project-plan-outline-add', 'Add an outline');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 40, 1, 'project-plan-outline-edit', 'Modify details of an existing outline');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 50, 1, 'project-plan-outline-delete', 'Delete an outline');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 60, 1, 'project-plan-outline-modify', 'Make changes to an outline');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 4, 70, 1, 'project-plan-activities-assign', 'Re-assign activities');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 50, 'Lists');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 5, 10, 4, 'project-lists-view', 'View lists');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 5, 20, 2, 'project-lists-add', 'Add a list');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 5, 30, 2, 'project-lists-edit', 'Modify details of an existing list');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 5, 40, 1, 'project-lists-delete', 'Delete a list');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 5, 50, 2, 'project-lists-modify', 'Make changes to list items');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 60, 'Discussion');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 10, 4, 'project-discussion-forums-view', 'View discussion forums');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 20, 1, 'project-discussion-forums-add', 'Add discussion forum');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 30, 1, 'project-discussion-forums-edit', 'Modify discussion forum');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 40, 1, 'project-discussion-forums-delete', 'Delete discussion forum');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 50, 4, 'project-discussion-topics-view', 'View forum topics');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 60, 2, 'project-discussion-topics-add', 'Add forum topics');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 70, 2, 'project-discussion-topics-edit', 'Modify forum topics');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 80, 2, 'project-discussion-topics-delete', 'Delete forum topics');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 90, 3, 'project-discussion-messages-add', 'Post messages');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 100, 3, 'project-discussion-messages-reply', 'Reply to messages');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 110, 2, 'project-discussion-messages-edit', 'Modify existing messages');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 6, 120, 2, 'project-discussion-messages-delete', 'Delete messages');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 70, 'Tickets');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 7, 10, 4, 'project-tickets-view', 'View tickets');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 7, 20, 3, 'project-tickets-add', 'Add a ticket');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 7, 30, 2, 'project-tickets-edit', 'Modify existing ticket');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 7, 40, 1, 'project-tickets-delete', 'Delete tickets');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 7, 50, 1, 'project-tickets-assign', 'Assign tickets');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 80, 'Document Library');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 10, 4, 'project-documents-view', 'View documents');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 20, 1, 'project-documents-folders-add', 'Create folders');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 30, 1, 'project-documents-folders-edit', 'Modify folders');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 40, 1, 'project-documents-folders-delete', 'Delete folders');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 50, 2, 'project-documents-files-upload', 'Upload files');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 60, 4, 'project-documents-files-download', 'Download files');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 70, 2, 'project-documents-files-rename', 'Rename files');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 8, 80, 1, 'project-documents-files-delete', 'Delete files');

INSERT INTO lookup_project_permission_category (group_id, level, description) VALUES (1, 90, 'Setup');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 9, 10, 1, 'project-setup-customize', 'Customize project features');
INSERT INTO lookup_project_permission (group_id, category_id, level, default_role, permission, description) VALUES (1, 9, 20, 1, 'project-setup-permissions', 'Configure project permissions');

-- UPDATES

UPDATE project_team SET userlevel = 1 WHERE user_id = enteredby;
UPDATE project_team SET userlevel = 2 WHERE user_id <> enteredby AND userlevel IS NULL;

UPDATE lookup_project_activity SET group_id = 1;
UPDATE lookup_project_status SET group_id = 1;
UPDATE lookup_project_loe SET group_id = 1;
UPDATE lookup_project_priority SET group_id = 1;

UPDATE permission_category SET enabled = 1, active = 1 WHERE category = 'Projects';

-- TODO: NEED TO INSERT EXISTING ASSIGNMENTS INTO REQUIREMENT_MAP

-- ACCESS table

ALTER TABLE access ADD currency VARCHAR(5);
ALTER TABLE access ADD language VARCHAR(20);
