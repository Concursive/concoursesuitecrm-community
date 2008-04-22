CREATE TABLE lookup_webpage_priority (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300),
  default_item boolean DEFAULT false,
  "level" integer,
  constant float,
  enabled boolean DEFAULT true,
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp NULL
) ;

CREATE TABLE lookup_sitechange_frequency (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300),
  default_item boolean DEFAULT false,
  "level" integer,
  constant VARCHAR(300),
  enabled boolean DEFAULT true,
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp NULL
);

alter table web_site add column url varchar (2000);

alter table web_page add column change_freq integer REFERENCES lookup_sitechange_frequency(code);
alter table web_page add column page_priority INT REFERENCES lookup_webpage_priority(code);