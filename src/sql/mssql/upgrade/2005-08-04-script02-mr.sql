-- Opportunity Access Types
ALTER TABLE opportunity_header ADD access_type INT REFERENCES lookup_access_types(code);
UPDATE opportunity_header SET access_type = (SELECT code FROM lookup_access_types WHERE link_module_id = 804051057 AND rule_id = 626030335);
ALTER TABLE opportunity_header ALTER COLUMN access_type INT NOT NULL;

ALTER TABLE opportunity_header ADD manager INT REFERENCES access(user_id);
UPDATE opportunity_header SET manager = (SELECT TOP 1 owner FROM opportunity_component oc WHERE opportunity_header.opp_id = oc.opp_id);
UPDATE opportunity_header SET manager = enteredby WHERE manager IS NULL;
ALTER TABLE opportunity_header ALTER COLUMN manager INT NOT NULL;
