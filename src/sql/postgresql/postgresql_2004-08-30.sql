-- Script (C) 2004 Dark Horse Ventures, all rights reserved
-- Database upgrade v2.9 (2004-08-30)

ALTER TABLE access ADD COLUMN currency character varying(5);
ALTER TABLE access ADD COLUMN "language" character varying(20);

ALTER TABLE organization ADD COLUMN alertdate_timezone character varying(255);
ALTER TABLE organization ADD COLUMN contract_end_timezone character varying(255);

ALTER table contact_address ADD COLUMN primary_address boolean;
ALTER table contact_address ALTER primary_address set default false;
UPDATE contact_address SET primary_address = false;
ALTER TABLE contact_address ADD CONSTRAINT contact_address_primaddr_not_null CHECK(primary_address IS NOT NULL);

ALTER table contact_phone ADD COLUMN primary_number boolean;
ALTER table contact_phone ALTER primary_number set default false;
UPDATE contact_phone SET primary_number = false;
ALTER TABLE contact_phone ADD CONSTRAINT contact_phone_primnum_not_null CHECK(primary_number IS NOT NULL);


CREATE TABLE lookup_call_priority (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    weight integer NOT NULL
);



CREATE TABLE lookup_call_reminder (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    base_value integer DEFAULT 0 NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE TABLE lookup_call_result (
    result_id serial NOT NULL,
    description character varying(100) NOT NULL,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    next_required boolean DEFAULT false NOT NULL,
    next_days integer DEFAULT 0 NOT NULL,
    next_call_type_id integer,
    canceled_type boolean DEFAULT false NOT NULL
);



ALTER TABLE opportunity_component ADD COLUMN alertdate_timezone character varying(255);

ALTER TABLE call_log ADD COLUMN alert_tmp VARCHAR(255);
UPDATE call_log SET alert_tmp = alert;
ALTER TABLE call_log DROP COLUMN alert;
ALTER TABLE call_log ADD COLUMN alert VARCHAR(255);
UPDATE call_log SET alert = alert_tmp;
ALTER TABLE call_log DROP COLUMN alert_tmp;

ALTER TABLE call_log ADD COLUMN alert_call_type_id integer;
ALTER TABLE call_log ADD COLUMN parent_id integer;
ALTER TABLE call_log ADD COLUMN "owner" integer;
ALTER TABLE call_log ADD COLUMN assignedby integer;

ALTER TABLE call_log ADD COLUMN assign_date TIMESTAMP(3);
ALTER TABLE call_log ALTER assign_date SET DEFAULT CURRENT_TIMESTAMP;
UPDATE call_log SET assign_date = CURRENT_TIMESTAMP;

ALTER TABLE call_log ADD COLUMN completedby integer;
ALTER TABLE call_log ADD COLUMN complete_date timestamp(3) without time zone;
ALTER TABLE call_log ADD COLUMN result_id integer;
ALTER TABLE call_log ADD COLUMN priority_id integer;

ALTER TABLE call_log ADD COLUMN status_id INT;
UPDATE call_log SET status_id = 2;
ALTER TABLE call_log ADD CONSTRAINT call_status_not_null CHECK(status_id IS NOT NULL);
ALTER TABLE call_log ALTER status_id SET DEFAULT 1;

ALTER TABLE call_log ADD COLUMN reminder_value integer;
ALTER TABLE call_log ADD COLUMN reminder_type_id integer;
UPDATE call_log SET result_id = 1;

ALTER table call_log ADD COLUMN alertdate_timezone VARCHAR(255);
ALTER table call_log ALTER alertdate_timezone set default 'America/New_York';
UPDATE call_log set alertdate_timezone = 'America/New_York';

ALTER TABLE project_issues DROP CONSTRAINT "$2";

DROP TABLE lookup_project_issues;

CREATE TABLE lookup_project_role (
    code serial NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
);



CREATE SEQUENCE lookup_project_cat_code_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE lookup_project_category (
    code integer DEFAULT nextval('lookup_project_cat_code_seq'::text) NOT NULL,
    description character varying(80) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
);



ALTER TABLE projects ADD COLUMN approvalby integer;
ALTER TABLE projects ADD COLUMN category_id integer;

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

ALTER TABLE projects ADD COLUMN news_label character varying(50);
ALTER TABLE projects ADD COLUMN details_label character varying(50);
ALTER TABLE projects ADD COLUMN team_label character varying(50);
ALTER TABLE projects ADD COLUMN plan_label character varying(50);
ALTER TABLE projects ADD COLUMN lists_label character varying(50);
ALTER TABLE projects ADD COLUMN discussion_label character varying(50);
ALTER TABLE projects ADD COLUMN tickets_label character varying(50);
ALTER TABLE projects ADD COLUMN documents_label character varying(50);
ALTER TABLE projects ADD COLUMN est_closedate timestamp(3) without time zone;
ALTER TABLE projects ADD COLUMN budget double precision;
ALTER TABLE projects ADD COLUMN budget_currency character varying(5);

ALTER TABLE project_requirements ADD COLUMN startdate timestamp(3) without time zone;

CREATE SEQUENCE project_assignmen_folder_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE project_assignments_folder (
    folder_id integer DEFAULT nextval('project_assignmen_folder_id_seq'::text) NOT NULL,
    parent_id integer,
    requirement_id integer NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);


ALTER TABLE project_assignments DROP COLUMN activity_id;
ALTER TABLE project_assignments ADD COLUMN folder_id integer;
ALTER TABLE project_assignments ADD COLUMN percent_complete integer;

CREATE SEQUENCE project_issue_cate_categ_id_seq
    START 1
    INCREMENT 1
    MAXVALUE 9223372036854775807
    MINVALUE 1
    CACHE 1;



CREATE TABLE project_issues_categories (
    category_id integer DEFAULT nextval('project_issue_cate_categ_id_seq'::text) NOT NULL,
    project_id integer NOT NULL,
    subject character varying(255) NOT NULL,
    description text,
    enabled boolean DEFAULT true NOT NULL,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL,
    topics_count integer DEFAULT 0 NOT NULL,
    posts_count integer DEFAULT 0 NOT NULL,
    last_post_date timestamp(3) without time zone,
    last_post_by integer
);

ALTER TABLE project_issues DROP COLUMN type_id;
ALTER TABLE project_issues ADD COLUMN category_id integer;

ALTER TABLE project_issues ADD COLUMN reply_count INTEGER;
ALTER TABLE project_issues ALTER reply_count SET DEFAULT 0;
UPDATE project_issues SET reply_count = 0 WHERE reply_count IS NULL;
ALTER TABLE project_issues ADD CONSTRAINT projects_issues_replycount_not_null CHECK(reply_count IS NOT NULL);

ALTER TABLE project_issues ADD COLUMN last_reply_date TIMESTAMP(3);
ALTER TABLE project_issues ALTER last_reply_date SET DEFAULT CURRENT_TIMESTAMP;
UPDATE project_issues SET last_reply_date = modified WHERE last_reply_date IS NULL;
ALTER TABLE project_issues ADD CONSTRAINT projects_issues_replydate_not_null CHECK(last_reply_date IS NOT NULL);

ALTER TABLE project_issues ADD COLUMN last_reply_by integer;

ALTER TABLE project_folders DROP COLUMN parent;
ALTER TABLE project_folders ADD COLUMN parent_id integer;

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

ALTER TABLE project_folders ADD COLUMN display integer;

CREATE TABLE project_files_thumbnail (
    item_id integer,
    filename character varying(255) NOT NULL,
    size integer DEFAULT 0,
    "version" double precision DEFAULT 0,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);


ALTER TABLE project_team ADD CONSTRAINT project_team_userlevel_not_null CHECK(userlevel IS NOT NULL);

ALTER TABLE project_team ADD COLUMN status integer;
ALTER TABLE project_team ADD COLUMN last_accessed timestamp(3) without time zone;

CREATE TABLE project_news (
    news_id serial NOT NULL,
    project_id integer NOT NULL,
    category_id integer,
    subject character varying(255) NOT NULL,
    intro character varying(2048),
    message text,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    modifiedby integer NOT NULL,
    start_date timestamp without time zone DEFAULT ('now'::text)::timestamp(6) with time zone,
    end_date timestamp without time zone,
    allow_replies boolean DEFAULT false,
    allow_rating boolean DEFAULT false,
    rating_count integer DEFAULT 0 NOT NULL,
    avg_rating double precision DEFAULT 0,
    priority_id integer DEFAULT 10,
    read_count integer DEFAULT 0 NOT NULL,
    enabled boolean DEFAULT true,
    status integer,
    html boolean DEFAULT true NOT NULL,
    start_date_timezone VARCHAR(255),
    end_date_timezone VARCHAR(255)
);



CREATE TABLE project_requirements_map (
    map_id serial NOT NULL,
    project_id integer NOT NULL,
    requirement_id integer NOT NULL,
    "position" integer NOT NULL,
    indent integer DEFAULT 0 NOT NULL,
    folder_id integer,
    assignment_id integer
);



CREATE TABLE lookup_project_permission_category (
    code serial NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL
);



CREATE TABLE lookup_project_permission (
    code serial NOT NULL,
    category_id integer,
    permission character varying(300) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true,
    group_id integer DEFAULT 0 NOT NULL,
    default_role integer
);



CREATE TABLE project_permissions (
    id serial NOT NULL,
    project_id integer NOT NULL,
    permission_id integer NOT NULL,
    userlevel integer NOT NULL
);


ALTER TABLE service_contract ADD COLUMN service_model_notes text;

ALTER TABLE ticket ADD COLUMN key_count integer;
ALTER TABLE ticket ADD COLUMN est_resolution_date_timezone character varying(255);

CREATE TABLE project_ticket_count (
    project_id integer NOT NULL,
    key_count integer DEFAULT 0 NOT NULL
);


CREATE TABLE ticketlink_project (
    ticket_id integer NOT NULL,
    project_id integer NOT NULL
);



ALTER TABLE task ADD COLUMN duedate_timezone character varying(255);

ALTER TABLE tasklink_contact ADD COLUMN notes text;


INSERT INTO lookup_call_priority VALUES (1, 'Low', true, 1, true, 10);
INSERT INTO lookup_call_priority VALUES (2, 'Medium', false, 2, true, 20);
INSERT INTO lookup_call_priority VALUES (3, 'High', false, 3, true, 30);



INSERT INTO lookup_call_reminder VALUES (1, 'Minute(s)', 60, true, 1, true);
INSERT INTO lookup_call_reminder VALUES (2, 'Hour(s)', 3600, false, 2, true);
INSERT INTO lookup_call_reminder VALUES (3, 'Day(s)', 86400, false, 3, true);
INSERT INTO lookup_call_reminder VALUES (4, 'Week(s)', 604800, false, 4, true);
INSERT INTO lookup_call_reminder VALUES (5, 'Month(s)', 18144000, false, 5, true);



INSERT INTO lookup_call_result VALUES (1, 'Yes - Business progressing', 10, true, true, 0, NULL, false);
INSERT INTO lookup_call_result VALUES (2, 'No - No business at this time', 20, true, false, 0, NULL, false);
INSERT INTO lookup_call_result VALUES (3, 'Unsure - Unsure or no contact made', 30, true, true, 0, NULL, false);
INSERT INTO lookup_call_result VALUES (4, 'Lost to competitor', 140, true, false, 0, NULL, true);
INSERT INTO lookup_call_result VALUES (5, 'No further interest', 150, true, false, 0, NULL, true);
INSERT INTO lookup_call_result VALUES (6, 'Event postponed/canceled', 160, true, false, 0, NULL, true);
INSERT INTO lookup_call_result VALUES (7, 'Another pending action', 170, true, false, 0, NULL, true);
INSERT INTO lookup_call_result VALUES (8, 'Another contact handling event', 180, true, false, 0, NULL, true);
INSERT INTO lookup_call_result VALUES (9, 'Contact no longer with company', 190, true, false, 0, NULL, true);
INSERT INTO lookup_call_result VALUES (10, 'Servicing', 120, true, false, 0, NULL, false);



INSERT INTO lookup_project_role VALUES (1, 'Project Lead', false, 10, true, 1);
INSERT INTO lookup_project_role VALUES (2, 'Contributor', false, 20, true, 1);
INSERT INTO lookup_project_role VALUES (3, 'Observer', false, 30, true, 1);
INSERT INTO lookup_project_role VALUES (4, 'Guest', false, 100, true, 1);



INSERT INTO lookup_project_permission_category VALUES (1, 'Project Details', false, 10, true, 1);
INSERT INTO lookup_project_permission_category VALUES (2, 'Team Members', false, 20, true, 1);
INSERT INTO lookup_project_permission_category VALUES (3, 'News', false, 30, true, 1);
INSERT INTO lookup_project_permission_category VALUES (4, 'Plan/Outlines', false, 40, true, 1);
INSERT INTO lookup_project_permission_category VALUES (5, 'Lists', false, 50, true, 1);
INSERT INTO lookup_project_permission_category VALUES (6, 'Discussion', false, 60, true, 1);
INSERT INTO lookup_project_permission_category VALUES (7, 'Tickets', false, 70, true, 1);
INSERT INTO lookup_project_permission_category VALUES (8, 'Document Library', false, 80, true, 1);
INSERT INTO lookup_project_permission_category VALUES (9, 'Setup', false, 90, true, 1);



INSERT INTO lookup_project_permission VALUES (1, 1, 'project-details-view', 'View project details', false, 10, true, 1, 4);
INSERT INTO lookup_project_permission VALUES (2, 1, 'project-details-edit', 'Modify project details', false, 20, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (3, 1, 'project-details-delete', 'Delete project', false, 30, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (4, 2, 'project-team-view', 'View team members', false, 10, true, 1, 4);
INSERT INTO lookup_project_permission VALUES (5, 2, 'project-team-view-email', 'See team member email addresses', false, 20, true, 1, 3);
INSERT INTO lookup_project_permission VALUES (6, 2, 'project-team-edit', 'Modify team', false, 30, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (7, 2, 'project-team-edit-role', 'Modify team member role', false, 40, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (8, 3, 'project-news-view', 'View current news', false, 10, true, 1, 4);
INSERT INTO lookup_project_permission VALUES (9, 3, 'project-news-view-unreleased', 'View unreleased news', false, 20, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (10, 3, 'project-news-view-archived', 'View archived news', false, 30, true, 1, 3);
INSERT INTO lookup_project_permission VALUES (11, 3, 'project-news-add', 'Add news', false, 40, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (12, 3, 'project-news-edit', 'Edit news', false, 50, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (13, 3, 'project-news-delete', 'Delete news', false, 60, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (14, 4, 'project-plan-view', 'View outlines', false, 10, true, 1, 4);
INSERT INTO lookup_project_permission VALUES (15, 4, 'project-plan-outline-add', 'Add an outline', false, 20, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (16, 4, 'project-plan-outline-edit', 'Modify details of an existing outline', false, 40, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (17, 4, 'project-plan-outline-delete', 'Delete an outline', false, 50, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (18, 4, 'project-plan-outline-modify', 'Make changes to an outline', false, 60, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (19, 4, 'project-plan-activities-assign', 'Re-assign activities', false, 70, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (20, 5, 'project-lists-view', 'View lists', false, 10, true, 1, 4);
INSERT INTO lookup_project_permission VALUES (21, 5, 'project-lists-add', 'Add a list', false, 20, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (22, 5, 'project-lists-edit', 'Modify details of an existing list', false, 30, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (23, 5, 'project-lists-delete', 'Delete a list', false, 40, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (24, 5, 'project-lists-modify', 'Make changes to list items', false, 50, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (25, 6, 'project-discussion-forums-view', 'View discussion forums', false, 10, true, 1, 4);
INSERT INTO lookup_project_permission VALUES (26, 6, 'project-discussion-forums-add', 'Add discussion forum', false, 20, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (27, 6, 'project-discussion-forums-edit', 'Modify discussion forum', false, 30, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (28, 6, 'project-discussion-forums-delete', 'Delete discussion forum', false, 40, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (29, 6, 'project-discussion-topics-view', 'View forum topics', false, 50, true, 1, 4);
INSERT INTO lookup_project_permission VALUES (30, 6, 'project-discussion-topics-add', 'Add forum topics', false, 60, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (31, 6, 'project-discussion-topics-edit', 'Modify forum topics', false, 70, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (32, 6, 'project-discussion-topics-delete', 'Delete forum topics', false, 80, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (33, 6, 'project-discussion-messages-add', 'Post messages', false, 90, true, 1, 3);
INSERT INTO lookup_project_permission VALUES (34, 6, 'project-discussion-messages-reply', 'Reply to messages', false, 100, true, 1, 3);
INSERT INTO lookup_project_permission VALUES (35, 6, 'project-discussion-messages-edit', 'Modify existing messages', false, 110, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (36, 6, 'project-discussion-messages-delete', 'Delete messages', false, 120, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (37, 7, 'project-tickets-view', 'View tickets', false, 10, true, 1, 4);
INSERT INTO lookup_project_permission VALUES (38, 7, 'project-tickets-add', 'Add a ticket', false, 20, true, 1, 3);
INSERT INTO lookup_project_permission VALUES (39, 7, 'project-tickets-edit', 'Modify existing ticket', false, 30, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (40, 7, 'project-tickets-delete', 'Delete tickets', false, 40, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (41, 7, 'project-tickets-assign', 'Assign tickets', false, 50, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (42, 8, 'project-documents-view', 'View documents', false, 10, true, 1, 4);
INSERT INTO lookup_project_permission VALUES (43, 8, 'project-documents-folders-add', 'Create folders', false, 20, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (44, 8, 'project-documents-folders-edit', 'Modify folders', false, 30, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (45, 8, 'project-documents-folders-delete', 'Delete folders', false, 40, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (46, 8, 'project-documents-files-upload', 'Upload files', false, 50, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (47, 8, 'project-documents-files-download', 'Download files', false, 60, true, 1, 4);
INSERT INTO lookup_project_permission VALUES (48, 8, 'project-documents-files-rename', 'Rename files', false, 70, true, 1, 2);
INSERT INTO lookup_project_permission VALUES (49, 8, 'project-documents-files-delete', 'Delete files', false, 80, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (50, 9, 'project-setup-customize', 'Customize project features', false, 10, true, 1, 1);
INSERT INTO lookup_project_permission VALUES (51, 9, 'project-setup-permissions', 'Configure project permissions', false, 20, true, 1, 1);



CREATE INDEX contact_address_contact_id_idx ON contact_address USING btree (contact_id);



CREATE INDEX contact_email_contact_id_idx ON contact_emailaddress USING btree (contact_id);



CREATE INDEX contact_phone_contact_id_idx ON contact_phone USING btree (contact_id);


CREATE INDEX call_log_entered_idx ON call_log USING btree (entered);



CREATE INDEX call_contact_id_idx ON call_log USING btree (contact_id);



CREATE INDEX call_org_id_idx ON call_log USING btree (org_id);



CREATE INDEX call_opp_id_idx ON call_log USING btree (opp_id);



DROP INDEX project_issues_idx;



CREATE UNIQUE INDEX project_team_uni_idx ON project_team USING btree (project_id, user_id);



CREATE INDEX proj_req_map_pr_req_pos_idx ON project_requirements_map USING btree (project_id, requirement_id, "position");



CREATE INDEX ticket_problem_idx ON ticket USING btree (problem);



CREATE INDEX ticket_comment_idx ON ticket USING btree ("comment");



CREATE INDEX ticket_solution_idx ON ticket USING btree (solution);



ALTER TABLE ONLY lookup_call_priority
    ADD CONSTRAINT lookup_call_priority_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_call_reminder
    ADD CONSTRAINT lookup_call_reminder_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_call_result
    ADD CONSTRAINT lookup_call_result_pkey PRIMARY KEY (result_id);



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$7" FOREIGN KEY (alert_call_type_id) REFERENCES lookup_call_types(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$8" FOREIGN KEY (parent_id) REFERENCES call_log(call_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$9" FOREIGN KEY ("owner") REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$10" FOREIGN KEY (assignedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$11" FOREIGN KEY (completedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$12" FOREIGN KEY (result_id) REFERENCES lookup_call_result(result_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$13" FOREIGN KEY (priority_id) REFERENCES lookup_call_priority(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY call_log
    ADD CONSTRAINT "$14" FOREIGN KEY (reminder_type_id) REFERENCES lookup_call_reminder(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_call_result
    ADD CONSTRAINT "$1" FOREIGN KEY (next_call_type_id) REFERENCES call_log(call_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_project_role
    ADD CONSTRAINT lookup_project_role_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_category
    ADD CONSTRAINT lookup_project_category_pkey PRIMARY KEY (code);



ALTER TABLE ONLY projects
    ADD CONSTRAINT "$4" FOREIGN KEY (approvalby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY projects
    ADD CONSTRAINT "$5" FOREIGN KEY (category_id) REFERENCES lookup_project_category(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT project_assignments_folder_pkey PRIMARY KEY (folder_id);



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT "$1" FOREIGN KEY (parent_id) REFERENCES project_assignments_folder(folder_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT "$2" FOREIGN KEY (requirement_id) REFERENCES project_requirements(requirement_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments_folder
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_assignments
    ADD CONSTRAINT "$12" FOREIGN KEY (folder_id) REFERENCES project_assignments_folder(folder_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issues_categories
    ADD CONSTRAINT project_issues_categories_pkey PRIMARY KEY (category_id);



ALTER TABLE ONLY project_issues_categories
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issues_categories
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issues_categories
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_issues
    ADD CONSTRAINT "$2" FOREIGN KEY (category_id) REFERENCES project_issues_categories(category_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_files_thumbnail
    ADD CONSTRAINT "$1" FOREIGN KEY (item_id) REFERENCES project_files(item_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_files_thumbnail
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_files_thumbnail
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_team
    ADD CONSTRAINT "$5" FOREIGN KEY (userlevel) REFERENCES lookup_project_role(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_news
    ADD CONSTRAINT project_news_pkey PRIMARY KEY (news_id);



ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$2" FOREIGN KEY (enteredby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_news
    ADD CONSTRAINT "$3" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements_map
    ADD CONSTRAINT project_requirements_map_pkey PRIMARY KEY (map_id);



ALTER TABLE ONLY project_requirements_map
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements_map
    ADD CONSTRAINT "$2" FOREIGN KEY (requirement_id) REFERENCES project_requirements(requirement_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements_map
    ADD CONSTRAINT "$3" FOREIGN KEY (folder_id) REFERENCES project_assignments_folder(folder_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_requirements_map
    ADD CONSTRAINT "$4" FOREIGN KEY (assignment_id) REFERENCES project_assignments(assignment_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_project_permission_category
    ADD CONSTRAINT lookup_project_permission_category_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_permission
    ADD CONSTRAINT lookup_project_permission_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_project_permission
    ADD CONSTRAINT lookup_project_permission_permission_key UNIQUE (permission);



ALTER TABLE ONLY lookup_project_permission
    ADD CONSTRAINT "$1" FOREIGN KEY (category_id) REFERENCES lookup_project_permission_category(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY lookup_project_permission
    ADD CONSTRAINT "$2" FOREIGN KEY (default_role) REFERENCES lookup_project_role(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_permissions
    ADD CONSTRAINT project_permissions_pkey PRIMARY KEY (id);



ALTER TABLE ONLY project_permissions
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_permissions
    ADD CONSTRAINT "$2" FOREIGN KEY (permission_id) REFERENCES lookup_project_permission(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_permissions
    ADD CONSTRAINT "$3" FOREIGN KEY (userlevel) REFERENCES lookup_project_role(code) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY project_ticket_count
    ADD CONSTRAINT project_ticket_count_project_id_key UNIQUE (project_id);



ALTER TABLE ONLY project_ticket_count
    ADD CONSTRAINT "$1" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticketlink_project
    ADD CONSTRAINT "$1" FOREIGN KEY (ticket_id) REFERENCES ticket(ticketid) ON UPDATE NO ACTION ON DELETE NO ACTION;



ALTER TABLE ONLY ticketlink_project
    ADD CONSTRAINT "$2" FOREIGN KEY (project_id) REFERENCES projects(project_id) ON UPDATE NO ACTION ON DELETE NO ACTION;



SELECT pg_catalog.setval ('lookup_call_priority_code_seq', 3, true);



SELECT pg_catalog.setval ('lookup_call_reminder_code_seq', 5, true);



SELECT pg_catalog.setval ('lookup_call_result_result_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_project_role_code_seq', 4, true);



SELECT pg_catalog.setval ('lookup_project_cat_code_seq', 1, false);



SELECT pg_catalog.setval ('project_assignmen_folder_id_seq', 1, false);



SELECT pg_catalog.setval ('project_issue_cate_categ_id_seq', 1, false);



SELECT pg_catalog.setval ('project_news_news_id_seq', 1, false);



SELECT pg_catalog.setval ('project_requirements_map_map_id_seq', 1, false);



SELECT pg_catalog.setval ('lookup_project_permission_category_code_seq', 9, true);



SELECT pg_catalog.setval ('lookup_project_permission_code_seq', 51, true);



SELECT pg_catalog.setval ('project_permissions_id_seq', 1, false);



UPDATE permission_category SET enabled = true, active = true WHERE category = 'Projects';

-- table time zone fields
ALTER TABLE opportunity_component ADD COLUMN closedate_timezone VARCHAR(255);
ALTER TABLE service_contract ADD COLUMN initial_start_date_timezone VARCHAR(255);
ALTER TABLE service_contract ADD COLUMN current_start_date_timezone VARCHAR(255);
ALTER TABLE service_contract ADD COLUMN current_end_date_timezone VARCHAR(255);
ALTER TABLE asset ADD COLUMN date_listed_timezone VARCHAR(255);
ALTER TABLE asset ADD COLUMN expiration_date_timezone VARCHAR(255);
ALTER TABLE asset ADD COLUMN purchase_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD COLUMN assigned_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD COLUMN resolution_date_timezone VARCHAR(255);
ALTER TABLE ticket_activity_item ADD COLUMN activity_date_timezone VARCHAR(255);
ALTER TABLE campaign ADD COLUMN active_date_timezone VARCHAR(255);
ALTER TABLE project_requirements ADD COLUMN startdate_timezone VARCHAR(255);
ALTER TABLE project_requirements ADD COLUMN deadline_timezone VARCHAR(255);
ALTER TABLE project_requirements ADD COLUMN due_date_timezone VARCHAR(255);
ALTER TABLE project_assignments ADD COLUMN due_date_timezone VARCHAR(255);
ALTER TABLE projects ADD COLUMN requestDate_timezone VARCHAR(255);
ALTER TABLE projects ADD COLUMN est_closedate_timezone VARCHAR(255);
ALTER TABLE ticket_csstm_form ADD COLUMN alert_date_timezone VARCHAR(255);

-- Renamed some permissions
UPDATE permission SET description = 'Activities' WHERE permission = 'pipeline-opportunities-calls';
UPDATE permission SET description = 'Contact Activities' WHERE permission = 'accounts-accounts-contacts-calls';
UPDATE permission SET description = 'Activities' WHERE permission = 'contacts-external_contacts-calls';

INSERT INTO database_version (script_filename, script_version) VALUES ('postgresql_2004-08-30.sql', '2004-08-30');

