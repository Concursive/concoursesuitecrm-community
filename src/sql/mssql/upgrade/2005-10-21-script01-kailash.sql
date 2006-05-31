/*
  Creates tables for lookup lists and required sequences
  
  Alters tables asset and ticket to reference the code in the newly
  added lookup lists.
*/
CREATE TABLE lookup_asset_manufacturer(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_asset_vendor(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE lookup_ticket_escalation(
  code INT IDENTITY PRIMARY KEY
  ,description VARCHAR(300)
  ,default_item BIT DEFAULT 0
  ,level INTEGER 
  ,enabled BIT DEFAULT 1
);

ALTER TABLE asset ADD vendor_code INT REFERENCES lookup_asset_vendor(code);
ALTER TABLE asset ADD manufacturer_code INT REFERENCES lookup_asset_manufacturer(code);
ALTER TABLE ticket ADD escalation_level INT REFERENCES lookup_ticket_escalation(code);
ALTER TABLE ticketlog ADD escalation_code INT REFERENCES lookup_ticket_escalation(code);

