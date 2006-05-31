CREATE TABLE lookup_account_size (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE lookup_segments (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE lookup_site_id (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  short_description VARCHAR(300),
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE lookup_sub_segment (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  segment_id  INT REFERENCES lookup_segments(code),
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE lookup_title (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

ALTER TABLE access ADD site_id INT REFERENCES lookup_site_id(code);

ALTER TABLE contact ADD no_email BIT DEFAULT 0;
UPDATE contact SET no_email = 0;
ALTER TABLE contact ADD no_mail BIT DEFAULT 0;
UPDATE contact SET no_mail = 0;
ALTER TABLE contact ADD no_phone BIT DEFAULT 0;
UPDATE contact SET no_phone = 0;
ALTER TABLE contact ADD no_textmessage BIT DEFAULT 0;
UPDATE contact SET no_textmessage = 0;
ALTER TABLE contact ADD no_im BIT DEFAULT 0;
UPDATE contact SET no_im = 0;
ALTER TABLE contact ADD no_fax BIT DEFAULT 0;
UPDATE contact SET no_fax = 0;
ALTER TABLE contact_address ADD addrline4 VARCHAR(80);

CREATE INDEX "oppcomplist_header_idx" ON "opportunity_component" (opp_id);

ALTER TABLE opportunity_component ADD status_id INT;

ALTER TABLE opportunity_header ADD lock BIT DEFAULT 0;
ALTER TABLE opportunity_header ADD contact_org_id INT;
ALTER TABLE opportunity_header ADD custom1_integer INT;

CREATE INDEX "opp_contactlink_idx" ON "opportunity_header" (contactlink);
CREATE INDEX "opp_header_contact_org_id_idx" ON "opportunity_header" (contact_org_id);
CREATE INDEX "oppheader_description_idx" ON "opportunity_header" (description);

ALTER TABLE organization ADD segment_id INT REFERENCES lookup_segments(code);
ALTER TABLE organization ADD sub_segment_id INT REFERENCES lookup_sub_segment(code);
ALTER TABLE organization ADD direct_bill BIT DEFAULT 0;
UPDATE organization SET direct_bill = 0 WHERE direct_bill IS NULL;
ALTER TABLE organization ADD account_size INT REFERENCES lookup_account_size(code);
ALTER TABLE organization ADD site_id INT REFERENCES lookup_site_id(code);
ALTER TABLE organization_address ADD addrline4 VARCHAR(80);

update lookup_delivery_options
set description='Broadcast', enabled=1
where code = 9;
UPDATE campaign SET send_method_id = 9 where send_method_id = 7;

update lookup_delivery_options
set description='Secure Socket', enabled=0
where code = 8;

update lookup_delivery_options
set description='Instant Message', enabled=0
where code = 7;

ALTER TABLE saved_criteriaelement ADD site_id INT;

