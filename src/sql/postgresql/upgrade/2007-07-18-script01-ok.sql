CREATE TABLE lookup_webpage_priority (
  code serial NOT NULL,
  description character varying(300),
  default_item boolean DEFAULT false,
  "level" integer,
  constant float,
  enabled boolean DEFAULT true,
  entered timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp(3)NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT lookup_webpage_prior_pkey PRIMARY KEY (code)
) ;

CREATE SEQUENCE lookup_sitechange_freq_code_seq;
CREATE TABLE lookup_sitechange_frequency (
  code INTEGER DEFAULT nextval('lookup_sitechange_freq_code_seq') NOT NULL PRIMARY KEY,
  description character varying(300),
  default_item boolean DEFAULT false,
  "level" integer,
  constant character varying(300),
  enabled boolean DEFAULT true,
  entered timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

alter table web_site add column "url" varchar (2000);

alter table web_page add column change_freq integer REFERENCES lookup_sitechange_frequency(code);
alter table web_page add column page_priority INT REFERENCES lookup_webpage_priority(code);
