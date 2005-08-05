
--Environment - What stuff is the account already using
CREATE SEQUENCE lookup_opportunity_env_code_seq;
CREATE TABLE lookup_opportunity_environment (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INT DEFAULT 0,
  enabled boolean DEFAULT true
);

--Competitors - Who else is competing for this business
CREATE SEQUENCE lookup_opportunity_com_code_seq;
CREATE TABLE lookup_opportunity_competitors (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INT DEFAULT 0,
  enabled boolean DEFAULT true
);

--Compelling Event - What event is driving the timeline for purchase
CREATE SEQUENCE lookup_opportunity_eve_code_seq;
CREATE TABLE lookup_opportunity_event_compelling (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INT DEFAULT 0,
  enabled boolean DEFAULT true
);

--Budget - Where are they getting the money to pay for the purchasse
CREATE SEQUENCE lookup_opportunity_bud_code_seq;
CREATE TABLE lookup_opportunity_budget (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INT DEFAULT 0,
  enabled boolean DEFAULT true
);

ALTER TABLE opportunity_component ADD COLUMN environment INT REFERENCES lookup_opportunity_environment(code),
ALTER TABLE opportunity_component ADD COLUMN competitors INT REFERENCES lookup_opportunity_competitors(code),
ALTER TABLE opportunity_component ADD COLUMN compelling_event INT REFERENCES lookup_opportunity_event_compelling(code),
ALTER TABLE opportunity_component ADD COLUMN budget INT REFERENCES lookup_opportunity_budget(code)

