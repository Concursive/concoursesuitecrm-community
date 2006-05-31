/*
  Creates tables for lookup lists and required sequences
  
  Alters tables asset and ticket to reference the code in the newly
  added lookup lists.
*/
CREATE SEQUENCE lookup_asset_manufactu_code_seq;
CREATE TABLE lookup_asset_manufacturer(
 code INTEGER DEFAULT nextval('lookup_asset_manufactu_code_seq') NOT NULL PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE lookup_asset_vendor(
 code SERIAL PRIMARY KEY,
 description VARCHAR(300),
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER,
 enabled BOOLEAN DEFAULT TRUE
);

CREATE SEQUENCE lookup_ticket_escalati_code_seq;
CREATE TABLE lookup_ticket_escalation(
  code INTEGER DEFAULT nextval('lookup_ticket_escalati_code_seq') NOT NULL PRIMARY KEY
  ,description VARCHAR(300) NOT NULL UNIQUE
  ,default_item BOOLEAN DEFAULT false
  ,level INTEGER DEFAULT 0
  ,enabled BOOLEAN DEFAULT true
);


ALTER TABLE asset ADD COLUMN vendor_code INT REFERENCES lookup_asset_vendor(code);
ALTER TABLE asset ADD COLUMN manufacturer_code INT REFERENCES lookup_asset_manufacturer(code);
ALTER TABLE ticket ADD COLUMN escalation_level INT REFERENCES lookup_ticket_escalation(code);
ALTER TABLE ticketlog ADD COLUMN escalation_code INT REFERENCES lookup_ticket_escalation(code);

