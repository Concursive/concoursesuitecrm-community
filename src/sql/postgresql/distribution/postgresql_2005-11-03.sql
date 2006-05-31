CREATE TABLE lookup_account_size (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_segments (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_site_id (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  short_description VARCHAR(300),
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_sub_segment (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  segment_id  INT REFERENCES lookup_segments(code),
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_title (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

ALTER TABLE access ADD COLUMN site_id INT REFERENCES lookup_site_id(code);

ALTER TABLE contact ADD COLUMN no_email BOOLEAN;
ALTER TABLE contact ALTER COLUMN no_email SET DEFAULT false;
UPDATE contact SET no_email = false;
ALTER TABLE contact ADD COLUMN no_mail BOOLEAN;
ALTER TABLE contact ALTER COLUMN no_mail SET DEFAULT false;
UPDATE contact SET no_mail = false;
ALTER TABLE contact ADD COLUMN no_phone BOOLEAN;
ALTER TABLE contact ALTER COLUMN no_phone SET DEFAULT false;
UPDATE contact SET no_phone = false;
ALTER TABLE contact ADD COLUMN no_textmessage BOOLEAN;
ALTER TABLE contact ALTER COLUMN no_textmessage SET DEFAULT false;
UPDATE contact SET no_textmessage = false;
ALTER TABLE contact ADD COLUMN no_im BOOLEAN;
ALTER TABLE contact ALTER COLUMN no_im SET DEFAULT false;
UPDATE contact SET no_im = false;
ALTER TABLE contact ADD COLUMN no_fax BOOLEAN;
ALTER TABLE contact ALTER COLUMN no_fax SET DEFAULT false;
UPDATE contact SET no_fax = false;

ALTER TABLE contact_address ADD COLUMN addrline4 VARCHAR(80);

CREATE INDEX "oppcomplist_header_idx" ON "opportunity_component" (opp_id);
ALTER TABLE opportunity_component ADD COLUMN status_id INTEGER;

ALTER TABLE opportunity_header ADD COLUMN lock BOOLEAN;
ALTER TABLE opportunity_header ALTER COLUMN lock SET DEFAULT false;
ALTER TABLE opportunity_header ADD COLUMN contact_org_id INTEGER;
ALTER TABLE opportunity_header ADD COLUMN custom1_integer INTEGER;

CREATE INDEX "opp_contactlink_idx" ON "opportunity_header" (contactlink);
CREATE INDEX "opp_header_contact_org_id_idx" ON "opportunity_header" (contact_org_id);
CREATE INDEX "oppheader_description_idx" ON "opportunity_header" (description);

ALTER TABLE organization ADD COLUMN segment_id INTEGER REFERENCES lookup_segments(code);
ALTER TABLE organization ADD COLUMN sub_segment_id INTEGER REFERENCES lookup_sub_segment(code);
ALTER TABLE organization ADD COLUMN direct_bill BOOLEAN;
ALTER TABLE organization ALTER COLUMN direct_bill SET DEFAULT false;
UPDATE organization SET direct_bill = false;
ALTER TABLE organization ADD COLUMN account_size INTEGER REFERENCES lookup_account_size(code);
ALTER TABLE organization_address ADD COLUMN addrline4 VARCHAR(80);
ALTER TABLE organization ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);

update lookup_delivery_options  set description='Broadcast', enabled=true  where code = 9;
UPDATE campaign SET send_method_id = 9 where send_method_id = 7;

update lookup_delivery_options  set description='Secure Socket', enabled=false  where code = 8;

update lookup_delivery_options  set description='Instant Message', enabled=false  where code = 7;

ALTER TABLE saved_criteriaelement ADD COLUMN site_id INT;
