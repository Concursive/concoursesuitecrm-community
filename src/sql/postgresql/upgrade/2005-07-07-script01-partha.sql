--Environment - What stuff is the account already using
CREATE SEQUENCE lookup_opportunity_env_code_seq;
CREATE TABLE lookup_opportunity_environment (
  code INTEGER DEFAULT nextval('lookup_opportunity_env_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

--Competitors - Who else is competing for this business
CREATE SEQUENCE lookup_opportunity_com_code_seq;
CREATE TABLE lookup_opportunity_competitors (
  code INTEGER DEFAULT nextval('lookup_opportunity_com_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

--Compelling Event - What event is driving the timeline for purchase
CREATE SEQUENCE lookup_opportunity_eve_code_seq;
CREATE TABLE lookup_opportunity_event_compelling (
  code INTEGER DEFAULT nextval('lookup_opportunity_eve_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

--Budget - Where are they getting the money to pay for the purchasse
CREATE SEQUENCE lookup_opportunity_bud_code_seq;
CREATE TABLE lookup_opportunity_budget (
  code INTEGER DEFAULT nextval('lookup_opportunity_bud_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

ALTER TABLE opportunity_component ADD COLUMN environment INT REFERENCES lookup_opportunity_environment(code);
ALTER TABLE opportunity_component ADD COLUMN competitors INT REFERENCES lookup_opportunity_competitors(code);
ALTER TABLE opportunity_component ADD COLUMN compelling_event INT REFERENCES lookup_opportunity_event_compelling(code);
ALTER TABLE opportunity_component ADD COLUMN budget INT REFERENCES lookup_opportunity_budget(code);

