-- PROJECTS

DROP INDEX project_issues_limit_idx;
DROP INDEX project_issues_idx;
ALTER TABLE project_issues DROP COLUMN type_id;
DROP TABLE lookup_project_issues;

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

ALTER TABLE projects ADD COLUMN approvalby INTEGER NULL REFERENCES access(user_id);
ALTER TABLE projects ADD COLUMN category_id INTEGER NULL REFERENCES lookup_project_category(code);

ALTER TABLE projects ADD COLUMN portal BOOLEAN;
ALTER TABLE projects ALTER portal SET DEFAULT false;
UPDATE projects SET portal = false WHERE portal IS NULL;
ALTER TABLE projects ADD CONSTRAINT projects_portal_not_null CHECK(portal IS NOT NULL);

ALTER TABLE projects ADD COLUMN allow_guests BOOLEAN;
ALTER TABLE projects ALTER allow_guests SET DEFAULT false;
UPDATE projects SET allow_guests = false WHERE allow_guests IS NULL;
ALTER TABLE projects ADD CONSTRAINT projects_allow_guests_not_null CHECK(allow_guests IS NOT NULL);

ALTER TABLE projects ADD COLUMN news_enabled BOOLEAN;
ALTER TABLE projects ALTER news_enabled SET DEFAULT true;
UPDATE projects SET news_enabled = true WHERE news_enabled IS NULL;
ALTER TABLE projects ADD CONSTRAINT projects_news_enabled_not_null CHECK(news_enabled IS NOT NULL);

ALTER TABLE projects ADD COLUMN details_enabled BOOLEAN;
ALTER TABLE projects ALTER details_enabled SET DEFAULT true;
UPDATE projects SET details_enabled = true WHERE details_enabled IS NULL;
ALTER TABLE projects ADD CONSTRAINT projects_details_enabled_not_null CHECK(details_enabled IS NOT NULL);

ALTER TABLE projects ADD COLUMN team_enabled BOOLEAN;
ALTER TABLE projects ALTER team_enabled SET DEFAULT true;
UPDATE projects SET team_enabled = true WHERE team_enabled IS NULL;
ALTER TABLE projects ADD CONSTRAINT projects_team_enabled_not_null CHECK(team_enabled IS NOT NULL);

ALTER TABLE projects ADD COLUMN plan_enabled BOOLEAN;
ALTER TABLE projects ALTER plan_enabled SET DEFAULT true;
UPDATE projects SET plan_enabled = true WHERE plan_enabled IS NULL;
ALTER TABLE projects ADD CONSTRAINT projects_plan_enabled_not_null CHECK(plan_enabled IS NOT NULL);

ALTER TABLE projects ADD COLUMN lists_enabled BOOLEAN;
ALTER TABLE projects ALTER lists_enabled SET DEFAULT true;
UPDATE projects SET lists_enabled = true WHERE lists_enabled IS NULL;
ALTER TABLE projects ADD CONSTRAINT projects_lists_enabled_not_null CHECK(lists_enabled IS NOT NULL);

ALTER TABLE projects ADD COLUMN discussion_enabled BOOLEAN;
ALTER TABLE projects ALTER discussion_enabled SET DEFAULT true;
UPDATE projects SET discussion_enabled = true WHERE discussion_enabled IS NULL;
ALTER TABLE projects ADD CONSTRAINT projects_discussion_enabled_not_null CHECK(discussion_enabled IS NOT NULL);

ALTER TABLE projects ADD COLUMN tickets_enabled BOOLEAN;
ALTER TABLE projects ALTER tickets_enabled SET DEFAULT true;
UPDATE projects SET tickets_enabled = true WHERE tickets_enabled IS NULL;
ALTER TABLE projects ADD CONSTRAINT projects_tickets_enabled_not_null CHECK(tickets_enabled IS NOT NULL);

ALTER TABLE projects ADD COLUMN documents_enabled BOOLEAN;
ALTER TABLE projects ALTER documents_enabled SET DEFAULT true;
UPDATE projects SET documents_enabled = true WHERE documents_enabled IS NULL;
ALTER TABLE projects ADD CONSTRAINT projects_documents_enabled_not_null CHECK(documents_enabled IS NOT NULL);

ALTER TABLE projects ADD COLUMN news_label VARCHAR(50) NULL;
ALTER TABLE projects ADD COLUMN details_label VARCHAR(50) NULL;
ALTER TABLE projects ADD COLUMN team_label VARCHAR(50) NULL;
ALTER TABLE projects ADD COLUMN plan_label VARCHAR(50) NULL;
ALTER TABLE projects ADD COLUMN lists_label VARCHAR(50) NULL;
ALTER TABLE projects ADD COLUMN discussion_label VARCHAR(50) NULL;
ALTER TABLE projects ADD COLUMN tickets_label VARCHAR(50) NULL;
ALTER TABLE projects ADD COLUMN documents_label VARCHAR(50) NULL;
ALTER TABLE projects ADD COLUMN est_closedate TIMESTAMP(3);
ALTER TABLE projects ADD COLUMN budget FLOAT;
ALTER TABLE projects ADD COLUMN budget_currency VARCHAR(5);


ALTER TABLE project_requirements ADD COLUMN startdate TIMESTAMP(3) NULL;

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

ALTER TABLE project_assignments DROP COLUMN activity_id;
ALTER TABLE project_assignments ADD COLUMN folder_id INTEGER NULL REFERENCES project_assignments_folder(folder_id);
ALTER TABLE project_assignments ADD COLUMN percent_complete INTEGER NULL;

--DROP INDEX project_assignments_idx;

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

ALTER TABLE project_issues ADD COLUMN category_id INTEGER NULL REFERENCES project_issues_categories(category_id);

ALTER TABLE project_issues ADD COLUMN reply_count INTEGER;
ALTER TABLE project_issues ALTER reply_count SET DEFAULT 0;
UPDATE project_issues SET reply_count = 0 WHERE reply_count IS NULL;
ALTER TABLE project_issues ADD CONSTRAINT projects_issues_replycount_not_null CHECK(reply_count IS NOT NULL);

ALTER TABLE project_issues ADD COLUMN last_reply_date TIMESTAMP(3);
ALTER TABLE project_issues ALTER last_reply_date SET DEFAULT CURRENT_TIMESTAMP;
UPDATE project_issues SET last_reply_date = modified WHERE last_reply_date IS NULL;
ALTER TABLE project_issues ADD CONSTRAINT projects_issues_replydate_not_null CHECK(last_reply_date IS NOT NULL);

ALTER TABLE project_issues ADD COLUMN last_reply_by INTEGER;

ALTER TABLE project_folders ADD COLUMN parent_id INTEGER NULL;

ALTER TABLE project_folders ADD COLUMN entered TIMESTAMP(3);
ALTER TABLE project_folders ALTER entered SET DEFAULT CURRENT_TIMESTAMP;
UPDATE project_folders SET entered = CURRENT_TIMESTAMP WHERE entered IS NULL;
ALTER TABLE project_folders ADD CONSTRAINT project_folders_entered_not_null CHECK(entered IS NOT NULL);

ALTER TABLE project_folders ADD COLUMN enteredBy INTEGER REFERENCES access(user_id);
UPDATE project_folders SET enteredBy = 0 WHERE enteredBy IS NULL;
ALTER TABLE project_folders ADD CONSTRAINT project_folders_enteredBy_not_null CHECK(enteredBy IS NOT NULL);

ALTER TABLE project_folders ADD COLUMN modified TIMESTAMP(3);
ALTER TABLE project_folders ALTER modified SET DEFAULT CURRENT_TIMESTAMP;
UPDATE project_folders SET modified = CURRENT_TIMESTAMP WHERE modified IS NULL;
ALTER TABLE project_folders ADD CONSTRAINT project_folders_modified_not_null CHECK(modified IS NOT NULL);

ALTER TABLE project_folders ADD COLUMN modifiedBy INTEGER REFERENCES access(user_id);
UPDATE project_folders SET modifiedBy = 0 WHERE modifiedBy IS NULL;
ALTER TABLE project_folders ADD CONSTRAINT project_folders_modifiedBy_not_null CHECK(modifiedBy IS NOT NULL);

ALTER TABLE project_folders ADD COLUMN display INTEGER NULL;

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

-- ADD REFERENCE userlevel INTEGER NOT NULL REFERENCES lookup_project_role(code),
ALTER TABLE project_team ADD COLUMN status INTEGER NULL;
ALTER TABLE project_team ADD COLUMN last_accessed TIMESTAMP(3);

CREATE UNIQUE INDEX project_team_uni_idx ON project_team (project_id, user_id);

CREATE TABLE project_news (
  news_id SERIAL,
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

-- TASKS
ALTER TABLE tasklink_contact ADD COLUMN notes TEXT;


-- TMS
CREATE TABLE ticketlink_project (
  ticket_id INT NOT NULL REFERENCES ticket(ticketid),
  project_id INT NOT NULL REFERENCES projects(project_id)
);

CREATE TABLE project_ticket_count (
  project_id INT UNIQUE NOT NULL REFERENCES projects(project_id),
  key_count INT NOT NULL DEFAULT 0
);

ALTER TABLE ticket ADD COLUMN key_count INT;

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

UPDATE permission_category SET enabled = true, active = true WHERE category = 'Projects';

-- TODO: NEED TO INSERT EXISTING ASSIGNMENTS INTO REQUIREMENT_MAP

-- ACCESS table

ALTER TABLE access ADD COLUMN currency VARCHAR(5);
ALTER TABLE access ADD COLUMN language VARCHAR(20);

