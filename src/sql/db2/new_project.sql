CREATE SEQUENCE lookup_projec_activit_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_project_activity(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL,
    template_id INTEGER DEFAULT 0,
    PRIMARY KEY(code)
);

CREATE SEQUENCE lookup_projec_priorit_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_project_priority(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL,
    graphic VARGRAPHIC(75),
    "type" INTEGER NOT NULL,
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_project_status_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_project_status(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL,
    graphic VARGRAPHIC(75),
    "type" INTEGER NOT NULL,
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_project_loe_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_project_loe(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    base_value INTEGER DEFAULT 0 NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_project_role_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_project_role(
    code INTEGER NOT NULL,
    description VARGRAPHIC(50) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_project_cat_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_project_category(
    code INTEGER NOT NULL,
    description VARGRAPHIC(80) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_news_template_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_news_template(
    code INTEGER NOT NULL,
    description VARGRAPHIC(255) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL,
    load_article CHAR(1) DEFAULT '0',
    load_project_article_list CHAR(1) DEFAULT '0',
    load_article_linked_list CHAR(1) DEFAULT '0',
    load_public_projects CHAR(1) DEFAULT '0',
    load_article_category_list CHAR(1) DEFAULT '0',
    mapped_jsp VARGRAPHIC(255) NOT NULL,
    PRIMARY KEY(code)
);


CREATE SEQUENCE projects_project_id_seq AS DECIMAL(27,0);


CREATE TABLE projects(
    project_id INTEGER NOT NULL,
    group_id INTEGER,
    department_id INTEGER REFERENCES lookup_department(code),
    template_id INTEGER,
    title VARGRAPHIC(100) NOT NULL,
    shortDescription VARGRAPHIC(200) NOT NULL,
    requestedBy VARGRAPHIC(50),
    requestedDept VARGRAPHIC(50),
    requestDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approvalDate TIMESTAMP,
    closeDate TIMESTAMP,
    owner INTEGER,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    approvalBy INTEGER REFERENCES "access"(user_id),
    category_id INTEGER REFERENCES lookup_project_category(code),
    portal CHAR(1) DEFAULT '0' NOT NULL,
    allow_guests CHAR(1) DEFAULT '0' NOT NULL,
    news_enabled CHAR(1) DEFAULT '1' NOT NULL,
    details_enabled CHAR(1) DEFAULT '1' NOT NULL,
    team_enabled CHAR(1) DEFAULT '1' NOT NULL,
    plan_enabled CHAR(1) DEFAULT '1' NOT NULL,
    lists_enabled CHAR(1) DEFAULT '1' NOT NULL,
    discussion_enabled CHAR(1) DEFAULT '1' NOT NULL,
    tickets_enabled CHAR(1) DEFAULT '1' NOT NULL,
    documents_enabled CHAR(1) DEFAULT '1' NOT NULL,
    news_label VARGRAPHIC(50),
    details_label VARGRAPHIC(50),
    team_label VARGRAPHIC(50),
    plan_label VARGRAPHIC(50),
    lists_label VARGRAPHIC(50),
    discussion_label VARGRAPHIC(50),
    tickets_label VARGRAPHIC(50),
    documents_label VARGRAPHIC(50),
    est_closedate TIMESTAMP,
    budget FLOAT,
    budget_currency VARGRAPHIC(5),
    requestDate_timezone VARGRAPHIC(255),
    est_closedate_timezone VARGRAPHIC(255),
    portal_default CHAR(1) DEFAULT '0' NOT NULL,
    portal_header VARGRAPHIC(255),
    portal_format VARGRAPHIC(255),
    portal_key VARGRAPHIC(255),
    portal_build_news_body CHAR(1) DEFAULT '0' NOT NULL,
    portal_news_menu CHAR(1) DEFAULT '0' NOT NULL,
    description CLOB(2G) NOT LOGGED,
    allows_user_observers CHAR(1) DEFAULT '0' NOT NULL,
    "level" INTEGER DEFAULT 10 NOT NULL,
    portal_page_type INTEGER,
    calendar_enabled CHAR(1) DEFAULT '1' NOT NULL,
    calendar_label VARGRAPHIC(50),
    accounts_enabled CHAR(1) DEFAULT '1' NOT NULL,
    accounts_label VARGRAPHIC(50),
    trashed_date TIMESTAMP,
    PRIMARY KEY(project_id)
);



CREATE INDEX projects_idx
    ON projects(group_id,project_id);


CREATE SEQUENCE project_requi_equirement_i_seq AS DECIMAL(27,0);


CREATE TABLE project_requirements(
    requirement_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id),
    submittedBy VARGRAPHIC(50),
    departmentBy VARGRAPHIC(30),
    shortDescription VARGRAPHIC(255) NOT NULL,
    description CLOB(2G) NOT LOGGED NOT NULL,
    dateReceived TIMESTAMP,
    estimated_loevalue INTEGER,
    estimated_loetype INTEGER REFERENCES lookup_project_loe(code),
    actual_loevalue INTEGER,
    actual_loetype INTEGER REFERENCES lookup_project_loe(code),
    deadline TIMESTAMP,
    approvedBy INTEGER REFERENCES "access"(user_id),
    approvalDate TIMESTAMP,
    closedBy INTEGER REFERENCES "access"(user_id),
    closeDate TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    startdate TIMESTAMP,
    startdate_timezone VARGRAPHIC(255),
    deadline_timezone VARGRAPHIC(255),
    due_date_timezone VARGRAPHIC(255),
    PRIMARY KEY(requirement_id)
);



CREATE SEQUENCE project_assig_en_folder_id_seq AS DECIMAL(27,0);


CREATE TABLE project_assignments_folder(
    folder_id INTEGER NOT NULL,
    parent_id INTEGER REFERENCES project_assignments_folder(folder_id),
    requirement_id INTEGER NOT NULL  REFERENCES project_requirements(requirement_id),
    name VARGRAPHIC(255) NOT NULL,
    description CLOB(2G) NOT LOGGED,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    PRIMARY KEY(folder_id)
);



CREATE SEQUENCE project_assig_ssignment_id_seq AS DECIMAL(27,0);


CREATE TABLE project_assignments(
    assignment_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id),
    requirement_id INTEGER REFERENCES project_requirements(requirement_id),
    assignedBy INTEGER REFERENCES "access"(user_id),
    user_assign_id INTEGER REFERENCES "access"(user_id),
    technology VARGRAPHIC(50),
    "role" VARGRAPHIC(255),
    estimated_loevalue INTEGER,
    estimated_loetype INTEGER REFERENCES lookup_project_loe(code),
    actual_loevalue INTEGER,
    actual_loetype INTEGER REFERENCES lookup_project_loe(code),
    priority_id INTEGER REFERENCES lookup_project_priority(code),
    assign_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    est_start_date TIMESTAMP,
    start_date TIMESTAMP,
    due_date TIMESTAMP,
    status_id INTEGER REFERENCES lookup_project_status(code),
    status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    complete_date TIMESTAMP,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    folder_id INTEGER REFERENCES project_assignments_folder(folder_id),
    percent_complete INTEGER,
    due_date_timezone VARGRAPHIC(255),
    PRIMARY KEY(assignment_id)
);


CREATE INDEX project_assignmen1
    ON project_assignments(complete_date,user_assign_id);


CREATE INDEX proj_assign_req_i1
    ON project_assignments(requirement_id);


CREATE SEQUENCE project_assig_en_status_id_seq AS DECIMAL(27,0);


CREATE TABLE project_assignments_status(
    status_id INTEGER NOT NULL,
    assignment_id INTEGER NOT NULL  REFERENCES project_assignments(assignment_id),
    user_id INTEGER NOT NULL  REFERENCES "access"(user_id),
    description CLOB(2G) NOT LOGGED NOT NULL,
    status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    percent_complete INTEGER,
    project_status_id INTEGER REFERENCES lookup_project_status(code),
    user_assign_id INTEGER REFERENCES "access"(user_id),
    PRIMARY KEY(status_id)
);


CREATE SEQUENCE project_issue_ate_categ_id_seq AS DECIMAL(27,0);


CREATE TABLE project_issues_categories(
    category_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id),
    subject VARGRAPHIC(255) NOT NULL,
    description CLOB(2G) NOT LOGGED,
    enabled CHAR(1) DEFAULT '1' NOT NULL,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    topics_count INTEGER DEFAULT 0,
    posts_count INTEGER DEFAULT 0,
    last_post_date TIMESTAMP,
    last_post_by INTEGER,
    allow_files CHAR(1) DEFAULT '0' NOT NULL,
    PRIMARY KEY(category_id)
);



CREATE SEQUENCE project_issues_issue_id_seq AS DECIMAL(27,0);


CREATE TABLE project_issues(
    issue_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id),
    subject VARGRAPHIC(255) NOT NULL,
    "message" CLOB(2G) NOT LOGGED NOT NULL,
    importance INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    category_id INTEGER REFERENCES project_issues_categories(category_id),
    reply_count INTEGER DEFAULT 0 NOT NULL,
    last_reply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_reply_by INTEGER,
    PRIMARY KEY(issue_id)
);


CREATE SEQUENCE project_issue_epl_reply_id_seq AS DECIMAL(27,0);


CREATE TABLE project_issue_replies(
    reply_id INTEGER NOT NULL,
    issue_id INTEGER NOT NULL  REFERENCES project_issues(issue_id),
    reply_to INTEGER DEFAULT 0,
    subject VARGRAPHIC(255) NOT NULL,
    "message" CLOB(2G) NOT LOGGED NOT NULL,
    importance INTEGER,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    PRIMARY KEY(reply_id)
);



CREATE SEQUENCE project_folders_folder_id_seq AS DECIMAL(27,0);


CREATE TABLE project_folders(
    folder_id INTEGER NOT NULL,
    link_module_id INTEGER NOT NULL,
    link_item_id INTEGER NOT NULL,
    subject VARGRAPHIC(255) NOT NULL,
    description CLOB(2G) NOT LOGGED,
    parent_id INTEGER,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    "display" INTEGER,
    PRIMARY KEY(folder_id)
);



CREATE SEQUENCE project_files_item_id_seq AS DECIMAL(27,0);


CREATE TABLE project_files(
    item_id INTEGER NOT NULL,
    link_module_id INTEGER NOT NULL,
    link_item_id INTEGER NOT NULL,
    folder_id INTEGER REFERENCES project_folders(folder_id),
    client_filename VARGRAPHIC(255) NOT NULL,
    filename VARGRAPHIC(255) NOT NULL,
    subject VARGRAPHIC(500) NOT NULL,
    "size" INTEGER DEFAULT 0,
    "version" FLOAT DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    downloads INTEGER DEFAULT 0,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    default_file CHAR(1) DEFAULT '0',
    PRIMARY KEY(item_id)
);


CREATE INDEX project_files_cidx
    ON project_files(link_module_id,link_item_id);


CREATE TABLE project_files_version(
    item_id INTEGER REFERENCES project_files(item_id),
    client_filename VARGRAPHIC(255) NOT NULL,
    filename VARGRAPHIC(255) NOT NULL,
    subject VARGRAPHIC(500) NOT NULL,
    "size" INTEGER DEFAULT 0,
    "version" FLOAT DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    downloads INTEGER DEFAULT 0,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id)
);



CREATE TABLE project_files_download(
    item_id INTEGER NOT NULL  REFERENCES project_files(item_id),
    "version" FLOAT DEFAULT 0,
    user_download_id INTEGER REFERENCES "access"(user_id),
    download_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);



CREATE TABLE project_files_thumbnail(
    item_id INTEGER REFERENCES project_files(item_id),
    filename VARGRAPHIC(255) NOT NULL,
    "size" INTEGER DEFAULT 0,
    "version" FLOAT DEFAULT 0,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    enteredBy INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modifiedBy INTEGER NOT NULL  REFERENCES "access"(user_id)
);



CREATE TABLE project_team(
    project_id INTEGER NOT NULL  REFERENCES projects(project_id),
    user_id INTEGER NOT NULL  REFERENCES "access"(user_id),
    userlevel INTEGER NOT NULL  REFERENCES lookup_project_role(code),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    status INTEGER,
    last_accessed TIMESTAMP
);



CREATE INDEX project_team_uni_1
    ON project_team(project_id,user_id);


CREATE SEQUENCE project_news___category_id_seq AS DECIMAL(27,0);


CREATE TABLE project_news_category(
    category_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id),
    category_name VARGRAPHIC(255),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "level" INTEGER DEFAULT 0 NOT NULL,
    enabled CHAR(1) DEFAULT '1',
    PRIMARY KEY(category_id)
);



CREATE SEQUENCE project_news_news_id_seq AS DECIMAL(27,0);


CREATE TABLE project_news(
    news_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id),
    category_id INTEGER REFERENCES project_news_category(category_id),
    subject VARGRAPHIC(255) NOT NULL,
    intro CLOB(2G) NOT LOGGED,
    "message" CLOB(2G) NOT LOGGED,
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enteredby INTEGER NOT NULL  REFERENCES "access"(user_id),
    modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifiedby INTEGER NOT NULL  REFERENCES "access"(user_id),
    start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP,
    allow_replies CHAR(1) DEFAULT '0',
    allow_rating CHAR(1) DEFAULT '0',
    rating_count INTEGER DEFAULT 0 NOT NULL,
    avg_rating FLOAT DEFAULT 0,
    priority_id INTEGER DEFAULT 10,
    read_count INTEGER DEFAULT 0 NOT NULL,
    enabled CHAR(1) DEFAULT '1',
    status INTEGER,
    html CHAR(1) DEFAULT '1' NOT NULL,
    start_date_timezone VARGRAPHIC(255),
    end_date_timezone VARGRAPHIC(255),
    classification_id INTEGER NOT NULL,
    template_id INTEGER REFERENCES lookup_news_template(code),
    PRIMARY KEY(news_id)
);


CREATE SEQUENCE project_requi_s_map_map_id_seq AS DECIMAL(27,0);


CREATE TABLE project_requirements_map(
    map_id INTEGER NOT NULL,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id),
    requirement_id INTEGER NOT NULL  REFERENCES project_requirements(requirement_id),
    "position" INTEGER NOT NULL,
    indent INTEGER DEFAULT 0 NOT NULL,
    folder_id INTEGER REFERENCES project_assignments_folder(folder_id),
    assignment_id INTEGER REFERENCES project_assignments(assignment_id),
    PRIMARY KEY(map_id)
);


CREATE INDEX proj_req_map_pr_r1
    ON project_requirements_map(project_id,requirement_id,"position");


CREATE SEQUENCE lookup_projec_ategory_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_project_perm_category(
    code INTEGER NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '0',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY(code)
);


CREATE SEQUENCE lookup_projec_mission_code_seq AS DECIMAL(27,0);


CREATE TABLE lookup_project_permission(
    code INTEGER NOT NULL,
    category_id INTEGER REFERENCES lookup_project_perm_category(code),
    permission VARGRAPHIC(300) NOT NULL,
    description VARGRAPHIC(300) NOT NULL,
    default_item CHAR(1) DEFAULT '1',
    "level" INTEGER DEFAULT 0,
    enabled CHAR(1) DEFAULT '1',
    group_id INTEGER DEFAULT 0 NOT NULL,
    default_role INTEGER REFERENCES lookup_project_role(code),
    PRIMARY KEY(code)
);


CREATE SEQUENCE project_permissions_id_seq AS DECIMAL(27,0);

CREATE TABLE project_permissions(
    id INTEGER NOT NULL,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id),
    permission_id INTEGER NOT NULL  REFERENCES lookup_project_permission(code),
    userlevel INTEGER NOT NULL  REFERENCES lookup_project_role(code),
    PRIMARY KEY(id)
);


CREATE SEQUENCE project_accounts_id_seq AS DECIMAL(27,0);


CREATE TABLE project_accounts(
    id INTEGER NOT NULL,
    project_id INTEGER NOT NULL  REFERENCES projects(project_id),
    org_id INTEGER NOT NULL  REFERENCES organization(org_id),
    entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);



CREATE INDEX proj_acct_project1
    ON project_accounts(project_id);


CREATE INDEX proj_acct_org_idx
    ON project_accounts(org_id);

