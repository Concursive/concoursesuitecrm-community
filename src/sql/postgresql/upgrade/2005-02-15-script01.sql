-- Project management updates

CREATE TABLE project_news_category (
  category_id SERIAL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  category_name VARCHAR(255),
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  level INTEGER NOT NULL DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$4" FOREIGN KEY (category_id) REFERENCES project_news_category(category_id);

ALTER TABLE projects ADD COLUMN portal_default BOOLEAN;
UPDATE projects SET portal_default = false;
ALTER TABLE projects ALTER COLUMN portal_default SET NOT NULL;
ALTER TABLE projects ALTER COLUMN portal_default SET DEFAULT false;

ALTER TABLE projects ADD COLUMN portal_header VARCHAR(255);
ALTER TABLE projects ADD COLUMN portal_format VARCHAR(255);
ALTER TABLE projects ADD COLUMN portal_key VARCHAR(255);

ALTER TABLE projects ADD COLUMN portal_build_news_body BOOLEAN;
UPDATE projects SET portal_build_news_body = false;
ALTER TABLE projects ALTER COLUMN portal_build_news_body SET NOT NULL;
ALTER TABLE projects ALTER COLUMN portal_build_news_body SET DEFAULT false;

ALTER TABLE projects ADD COLUMN portal_news_menu BOOLEAN;
UPDATE projects SET portal_news_menu = false;
ALTER TABLE projects ALTER COLUMN portal_news_menu SET NOT NULL;
ALTER TABLE projects ALTER COLUMN portal_news_menu SET DEFAULT false;

-- Increases size of task description field
ALTER TABLE task RENAME COLUMN description TO description_old;
ALTER TABLE task ADD COLUMN description VARCHAR(255);
UPDATE task SET description = description_old;
ALTER TABLE task DROP COLUMN description_old;

CREATE TABLE taskcategorylink_news (
  news_id INTEGER NOT NULL REFERENCES project_news(news_id),
  category_id INTEGER NOT NULL REFERENCES lookup_task_category
);

CREATE TABLE lookup_ticket_status (
  code serial PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
);

ALTER TABLE projects ADD COLUMN description TEXT;

ALTER TABLE projects ADD COLUMN allows_user_observers BOOLEAN;
UPDATE projects SET allows_user_observers = false;
ALTER TABLE projects ALTER COLUMN allows_user_observers SET DEFAULT false;
ALTER TABLE projects ALTER COLUMN allows_user_observers SET NOT NULL;

ALTER TABLE projects ADD COLUMN level INTEGER;
UPDATE projects SET level = 10;
ALTER TABLE projects ALTER COLUMN level SET DEFAULT 10;
ALTER TABLE projects ALTER COLUMN level SET NOT NULL;

ALTER TABLE ticket ADD COLUMN status_id INTEGER REFERENCES lookup_ticket_status(code);

CREATE TABLE lookup_news_template (
  code SERIAL PRIMARY KEY,
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

--INSERT INTO lookup_news_template (group_id, level, description, mapped_jsp, load_article) VALUES (1, 10, 'Article', 'templateArticle.jsp', true);
--INSERT INTO lookup_news_template (group_id, level, description, mapped_jsp, load_article, load_article_linked_list) VALUES (1, 20, 'Article + Linked List', 'templateArticle.jsp', true, true);
--INSERT INTO lookup_news_template (group_id, level, description, mapped_jsp, load_article, load_project_article_list, load_public_projects) VALUES (1, 30, 'Article + Article List + Public Project List', 'templateArticleNewsProjects.jsp', true, true, true);
--INSERT INTO lookup_news_template (group_id, level, description, mapped_jsp, load_article_category_list) VALUES (1, 40, 'Article List by Categories', 'templateNewsByCategory.jsp', true);
--INSERT INTO lookup_news_template (group_id, level, description, mapped_jsp, load_project_article_list, load_public_projects) VALUES (1, 50, 'Article List + Public Project List', 'templateArticle.jsp', true, true);

ALTER TABLE project_news ADD COLUMN classification_id INTEGER;
UPDATE project_news SET classification_id = 20;
ALTER TABLE project_news ALTER COLUMN classification_id SET NOT NULL;

ALTER TABLE project_news ADD COLUMN template_id INTEGER REFERENCES lookup_news_template;
--UPDATE project_news SET template_id = 1;

-- Update size of column
ALTER TABLE project_issue_replies RENAME COLUMN subject TO subject_old;
ALTER TABLE project_issue_replies ADD COLUMN subject VARCHAR(255);
UPDATE project_issue_replies SET subject = subject_old;
ALTER TABLE project_issue_replies DROP subject_old;

ALTER TABLE projects ADD COLUMN portal_page_type INTEGER;

ALTER TABLE projects ADD COLUMN calendar_enabled BOOLEAN;
UPDATE projects SET calendar_enabled = true;
ALTER TABLE projects ALTER COLUMN calendar_enabled SET DEFAULT true;
ALTER TABLE projects ALTER COLUMN calendar_enabled SET NOT NULL;

ALTER TABLE projects ADD COLUMN calendar_label VARCHAR(50) NULL;

CREATE TABLE timesheet (
  timesheet_id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  entry_date TIMESTAMP(3) NOT NULL,
  hours FLOAT NOT NULL DEFAULT 0,
  start_time TIMESTAMP NULL,
  end_time TIMESTAMP(3) NULL,
  verified BOOLEAN NOT NULL DEFAULT false,
  approved BOOLEAN NOT NULL DEFAULT false,
  approved_by INTEGER REFERENCES access(user_id),
  available BOOLEAN NOT NULL DEFAULT true,
  unavailable BOOLEAN NOT NULL DEFAULT false,
  vacation BOOLEAN NOT NULL DEFAULT false,
  vacation_approved BOOLEAN NOT NULL DEFAULT false,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES access(user_id)
);

CREATE TABLE timesheet_projects (
  id SERIAL PRIMARY KEY,
  timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
  project_id INTEGER REFERENCES projects(project_id),
  hours FLOAT NOT NULL DEFAULT 0,
  start_time TIMESTAMP(3) NULL,
  end_time TIMESTAMP(3) NULL
);

CREATE INDEX proj_assign_req_id_idx ON project_assignments USING btree (requirement_id);
  
ALTER TABLE project_assignments_status ADD COLUMN percent_complete INTEGER NULL;
ALTER TABLE project_assignments_status ADD COLUMN project_status_id INTEGER REFERENCES lookup_project_status;
ALTER TABLE project_assignments_status ADD COLUMN user_assign_id INTEGER NULL REFERENCES access(user_id);

ALTER TABLE lookup_task_category ADD description_tmp VARCHAR(255);
UPDATE lookup_task_category SET description_tmp = description;
ALTER TABLE lookup_task_category DROP COLUMN description;
ALTER TABLE lookup_task_category RENAME description_tmp TO description;
ALTER TABLE lookup_task_category ALTER description SET NOT NULL;

ALTER TABLE project_news ADD intro_tmp TEXT;
UPDATE project_news SET intro_tmp = intro;
ALTER TABLE project_news DROP COLUMN intro;
ALTER TABLE project_news RENAME intro_tmp TO intro;

CREATE INDEX project_team_user_idx ON project_team(user_id);
CREATE INDEX project_perm_proj_idx ON project_permissions(project_id);

ALTER TABLE project_issues_categories ADD COLUMN allow_files BOOLEAN;
UPDATE project_issues_categories SET allow_files = false;
ALTER TABLE project_issues_categories ALTER COLUMN allow_files SET DEFAULT false;
ALTER TABLE project_issues_categories ALTER COLUMN allow_files SET NOT NULL;
