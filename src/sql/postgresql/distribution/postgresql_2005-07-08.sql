-- Script (C) 2005 Dark Horse Ventures, all rights reserved
-- Database upgrade v3.1 (2005-07-08)

DELETE FROM events WHERE task = 'org.aspcfs.apps.users.UserCleanup#doTask';
DELETE FROM events WHERE extrainfo = 'http://${WEBSERVER.URL}/ProcessSystem.do?command=ClearGraphData';
DELETE FROM events WHERE task = 'org.aspcfs.apps.reportRunner.ReportCleanup#doTask';
DELETE FROM events WHERE task = 'org.aspcfs.apps.reportRunner.ReportRunner#doTask';
DELETE FROM events WHERE task = 'org.aspcfs.modules.base.Trasher#doTask';
DELETE FROM events WHERE task = 'org.aspcfs.apps.notifier.Notifier#doTask';

ALTER TABLE organization ADD COLUMN trashed_date timestamp(3);

ALTER TABLE contact DROP COLUMN imname;
ALTER TABLE contact DROP COLUMN imservice;
ALTER TABLE contact ADD COLUMN additional_names VARCHAR(255);
ALTER TABLE contact ADD COLUMN nickname VARCHAR(80);
ALTER TABLE contact ADD COLUMN role VARCHAR(255);
ALTER TABLE contact ADD COLUMN trashed_date timestamp(3);

ALTER TABLE relationship ADD COLUMN trashed_date timestamp(3);

ALTER TABLE opportunity_header ADD COLUMN trashed_date timestamp(3);

ALTER TABLE opportunity_component ADD COLUMN trashed_date timestamp(3);

ALTER TABLE call_log ADD COLUMN trashed_date timestamp(3);

ALTER TABLE projects ADD COLUMN trashed_date timestamp(3);

ALTER TABLE product_catalog ADD COLUMN trashed_date timestamp(3);
ALTER TABLE product_catalog ADD COLUMN active boolean;
ALTER TABLE product_catalog ALTER COLUMN active SET DEFAULT true;
UPDATE product_catalog SET active = enabled;
UPDATE product_catalog SET enabled = true;
ALTER TABLE product_catalog ALTER COLUMN active SET NOT NULL;

ALTER TABLE service_contract ADD COLUMN trashed_date timestamp(3);

ALTER TABLE asset ADD COLUMN trashed_date timestamp(3);

ALTER TABLE ticket ADD COLUMN trashed_date timestamp(3);

ALTER TABLE quote_entry ADD COLUMN trashed_date timestamp(3);

ALTER TABLE campaign ADD COLUMN cc VARCHAR(1024);
ALTER TABLE campaign ADD COLUMN bcc VARCHAR(1024);
ALTER TABLE campaign ADD COLUMN trashed_date timestamp(3);

ALTER TABLE task ADD COLUMN trashed_date timestamp(3);

ALTER TABLE document_store ADD COLUMN trashed_date timestamp(3);

ALTER TABLE business_process_hook ADD COLUMN priority integer;
UPDATE business_process_hook SET priority = 1 WHERE priority IS NULL;
ALTER TABLE business_process_hook ALTER COLUMN priority SET DEFAULT 0;
ALTER TABLE business_process_hook ALTER COLUMN priority SET NOT NULL;

CREATE TABLE history (
    history_id serial NOT NULL,
    contact_id integer,
    org_id integer,
    link_object_id integer NOT NULL,
    link_item_id integer,
    status character varying(255),
    "type" character varying(255),
    description text,
    "level" integer DEFAULT 10,
    enabled boolean DEFAULT true,
    entered timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    enteredby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT ('now'::text)::timestamp(6) with time zone NOT NULL,
    modifiedby integer NOT NULL
);

ALTER TABLE ONLY history
    ADD CONSTRAINT history_pkey PRIMARY KEY (history_id);



ALTER TABLE ONLY history
    ADD CONSTRAINT "$1" FOREIGN KEY (contact_id) REFERENCES contact(contact_id);



ALTER TABLE ONLY history
    ADD CONSTRAINT "$2" FOREIGN KEY (org_id) REFERENCES organization(org_id);



ALTER TABLE ONLY history
    ADD CONSTRAINT "$3" FOREIGN KEY (enteredby) REFERENCES "access"(user_id);



ALTER TABLE ONLY history
    ADD CONSTRAINT "$4" FOREIGN KEY (modifiedby) REFERENCES "access"(user_id);



SELECT pg_catalog.setval('history_history_id_seq', 1, false);

ALTER TABLE sites ADD COLUMN language VARCHAR(11);
UPDATE sites SET language = 'en_US';


CREATE SEQUENCE lookup_opportunity_env_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_opportunity_environment (
    code integer DEFAULT nextval('lookup_opportunity_env_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_opportunity_com_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_opportunity_competitors (
    code integer DEFAULT nextval('lookup_opportunity_com_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_opportunity_eve_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_opportunity_event_compelling (
    code integer DEFAULT nextval('lookup_opportunity_eve_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



CREATE SEQUENCE lookup_opportunity_bud_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



CREATE TABLE lookup_opportunity_budget (
    code integer DEFAULT nextval('lookup_opportunity_bud_code_seq'::text) NOT NULL,
    description character varying(300) NOT NULL,
    default_item boolean DEFAULT false,
    "level" integer DEFAULT 0,
    enabled boolean DEFAULT true
);



ALTER TABLE opportunity_component ADD COLUMN environment integer;
ALTER TABLE opportunity_component ADD COLUMN competitors integer;
ALTER TABLE opportunity_component ADD COLUMN compelling_event integer;
ALTER TABLE opportunity_component ADD COLUMN budget integer;

ALTER TABLE ONLY lookup_opportunity_environment
    ADD CONSTRAINT lookup_opportunity_environment_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_opportunity_competitors
    ADD CONSTRAINT lookup_opportunity_competitors_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_opportunity_event_compelling
    ADD CONSTRAINT lookup_opportunity_event_compelling_pkey PRIMARY KEY (code);



ALTER TABLE ONLY lookup_opportunity_budget
    ADD CONSTRAINT lookup_opportunity_budget_pkey PRIMARY KEY (code);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$6" FOREIGN KEY (environment) REFERENCES lookup_opportunity_environment(code);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$7" FOREIGN KEY (competitors) REFERENCES lookup_opportunity_competitors(code);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$8" FOREIGN KEY (compelling_event) REFERENCES lookup_opportunity_event_compelling(code);



ALTER TABLE ONLY opportunity_component
    ADD CONSTRAINT "$9" FOREIGN KEY (budget) REFERENCES lookup_opportunity_budget(code);



SELECT pg_catalog.setval('lookup_opportunity_env_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_opportunity_com_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_opportunity_eve_code_seq', 1, false);



SELECT pg_catalog.setval('lookup_opportunity_bud_code_seq', 1, false);






INSERT INTO database_version (script_filename, script_version) VALUES ('postgresql_2005-07-08.sql', '2005-07-08');
