CREATE SEQUENCE lookup_account_size_code_seq;
CREATE TABLE lookup_account_size (
  code INT PRIMARY KEY, 
  description VARCHAR(300) NOT NULL, 
  default_item boolean DEFAULT false, 
  "level" INTEGER DEFAULT 0, 
  enabled boolean DEFAULT true 
);

CREATE SEQUENCE lookup_segments_code_seq;
CREATE TABLE lookup_segments (
  code INT PRIMARY KEY, 
  description VARCHAR(300) NOT NULL, 
  default_item boolean DEFAULT false, 
  "level" INTEGER DEFAULT 0, 
  enabled boolean DEFAULT true 
);

CREATE SEQUENCE lookup_site_id_code_seq;
CREATE TABLE lookup_site_id (
  code INT PRIMARY KEY, 
  description VARCHAR(300) NOT NULL, 
  short_description VARCHAR(300), 
  default_item boolean DEFAULT false, 
  "level" INTEGER DEFAULT 0, 
  enabled boolean DEFAULT true 
);

CREATE SEQUENCE lookup_sub_segment_code_seq;
CREATE TABLE lookup_sub_segment (
  code INT PRIMARY KEY, 
  description VARCHAR(300) NOT NULL, 
  segment_id  INT REFERENCES lookup_segments(code),
  default_item boolean DEFAULT false, 
  "level" INTEGER DEFAULT 0, 
  enabled boolean DEFAULT true 
);

CREATE SEQUENCE lookup_title_code_seq;
CREATE TABLE lookup_title (
  code INT PRIMARY KEY, 
  description VARCHAR(300) NOT NULL, 
  default_item boolean DEFAULT false, 
  "level" INTEGER DEFAULT 0, 
  enabled boolean DEFAULT true 
);

ALTER TABLE access
  ADD COLUMN site_id INTEGER;
ALTER TABLE access
  ADD CONSTRAINT site_id_fkey FOREIGN KEY (site_id)
    REFERENCES lookup_site_id(code);

ALTER TABLE contact
  ADD COLUMN no_email boolean;
ALTER TABLE contact
  ALTER COLUMN no_email SET DEFAULT false;
ALTER TABLE contact
  ADD COLUMN no_mail boolean;
ALTER TABLE contact
  ALTER COLUMN no_mail SET DEFAULT false;
ALTER TABLE contact
  ADD COLUMN no_phone boolean;
ALTER TABLE contact
  ALTER COLUMN no_phone SET DEFAULT false;
ALTER TABLE contact
  ADD COLUMN no_textmessage boolean;
ALTER TABLE contact
  ALTER COLUMN no_textmessage SET DEFAULT false;
ALTER TABLE contact
  ADD COLUMN no_im boolean;
ALTER TABLE contact
  ALTER COLUMN no_im SET DEFAULT false;
ALTER TABLE contact
  ADD COLUMN no_fax boolean;
ALTER TABLE contact
  ALTER COLUMN no_fax SET DEFAULT false;
ALTER TABLE contact_address
  ADD COLUMN addrline4 VARCHAR(80);

CREATE INDEX "oppcomplist_header_idx" ON "opportunity_component" (opp_id);
ALTER TABLE opportunity_component
  ADD COLUMN status_id INTEGER;

ALTER TABLE opportunity_header
  ADD COLUMN lock boolean;
ALTER TABLE opportunity_header
  ALTER COLUMN lock SET DEFAULT false;
ALTER TABLE opportunity_header
  ADD COLUMN contact_org_id INTEGER;
ALTER TABLE opportunity_header
  ADD COLUMN custom1_integer INTEGER;

CREATE INDEX "opp_contactlink_idx" ON "opportunity_header" (contactlink);
CREATE INDEX "opp_header_contact_org_id_idx" ON "opportunity_header" (contact_org_id);
CREATE INDEX "oppheader_description_idx" ON "opportunity_header" (description);

ALTER TABLE organization
  ADD COLUMN segment_id INTEGER;
ALTER TABLE organization
  ADD CONSTRAINT segment_id_fkey FOREIGN KEY (segment_id)
    REFERENCES lookup_segments(code);
ALTER TABLE organization
  ADD COLUMN sub_segment_id INTEGER;
ALTER TABLE organization
  ADD CONSTRAINT sub_segment_id_fkey FOREIGN KEY (sub_segment_id)
    REFERENCES lookup_sub_segment(code);
ALTER TABLE organization
  ADD COLUMN direct_bill boolean;
ALTER TABLE organization
  ALTER COLUMN direct_bill SET DEFAULT false;
ALTER TABLE organization
  ADD COLUMN account_size INTEGER;
ALTER TABLE organization
  ADD CONSTRAINT account_size_fkey FOREIGN KEY (account_size)
    REFERENCES lookup_account_size(code);
ALTER TABLE organization_address
  ADD COLUMN addrline4 VARCHAR(80);
ALTER TABLE organization
  ADD COLUMN site_id INTEGER;
ALTER TABLE organization
  ADD CONSTRAINT site_id_fkey FOREIGN KEY (site_id)
    REFERENCES lookup_site_id(code);

update lookup_delivery_options
set description='Broadcast', enabled=true
where code = 9;
UPDATE campaign SET send_method_id = 9 where send_method_id = 7;

update lookup_delivery_options
set description='Secure Socket', enabled=false
where code = 8;

update lookup_delivery_options
set description='Instant Message', enabled=false
where code = 7;
