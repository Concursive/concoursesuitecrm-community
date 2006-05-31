-- Script (C) 2005 Dark Horse Ventures, all rights reserved
-- Database upgrade v3.2 part 2 (2005-08-24)

-- Opportunity Access Types
ALTER TABLE opportunity_header ADD COLUMN access_type INT REFERENCES lookup_access_types(code);
UPDATE opportunity_header SET access_type = (SELECT code FROM lookup_access_types WHERE link_module_id = 804051057 AND rule_id = 626030335);
ALTER TABLE opportunity_header ALTER COLUMN access_type SET NOT NULL;

ALTER TABLE opportunity_header ADD COLUMN manager INT REFERENCES access(user_id);
UPDATE opportunity_header SET manager = (SELECT owner FROM opportunity_component oc WHERE opportunity_header.opp_id = oc.opp_id LIMIT 1);
UPDATE opportunity_header SET manager = enteredby WHERE manager IS NULL;
ALTER TABLE opportunity_header ALTER COLUMN manager SET NOT NULL;


-- TODO: Use .bsh to retrieve translated description

UPDATE lookup_delivery_options SET description = 'Email Only' WHERE code = 1;
UPDATE lookup_delivery_options SET description = 'Fax only' WHERE code = 2;
UPDATE lookup_delivery_options SET description = 'Letter only' WHERE code = 3;
UPDATE lookup_delivery_options SET description = 'Email then Fax' WHERE code = 4;
UPDATE lookup_delivery_options SET description = 'Email then Letter' WHERE code = 5;
UPDATE lookup_delivery_options SET description = 'Email, Fax, then Letter' WHERE code = 6;
UPDATE lookup_delivery_options SET description = 'Instant Message', enabled = false WHERE code = 7;
UPDATE lookup_delivery_options SET description = 'Secure Socket', enabled = false WHERE code = 8;
UPDATE lookup_delivery_options SET description = 'Broadcast', enabled = true WHERE code = 9;
UPDATE campaign SET send_method_id = 9 where send_method_id = 7;

-- Add new colum to contact relation

ALTER TABLE contact ADD secret_word VARCHAR(255);
ALTER TABLE contact ADD account_number varchar(50);
ALTER TABLE contact ADD revenue FLOAT;
ALTER TABLE contact ADD industry_temp_code INTEGER REFERENCES lookup_industry(code);
ALTER TABLE contact ADD potential float;

CREATE INDEX contact_address_postalcode_idx ON contact_address(postalcode);
CREATE INDEX organization_address_postalcode_idx ON organization_address(postalcode);
CREATE INDEX "contact_city_idx" on contact_address(city);

ALTER TABLE organization ADD source integer REFERENCES lookup_contact_source(code);
ALTER TABLE organization ADD rating integer REFERENCES lookup_contact_rating(code);
ALTER TABLE organization ADD potential float;

