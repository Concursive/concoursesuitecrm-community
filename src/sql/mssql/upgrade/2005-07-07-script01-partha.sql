
--Environment - What stuff is the account already using
CREATE TABLE lookup_opportunity_environment (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INT DEFAULT 0,
  enabled BIT DEFAULT 1
);

--Competitors - Who else is competing for this business
CREATE TABLE lookup_opportunity_competitors (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INT DEFAULT 0,
  enabled BIT DEFAULT 1
);

--Compelling Event - What event is driving the timeline for purchase
CREATE TABLE lookup_opportunity_event_compelling (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INT DEFAULT 0,
  enabled BIT DEFAULT 1
);

--Budget - Where are they getting the money to pay for the purchasse
CREATE TABLE lookup_opportunity_budget (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INT DEFAULT 0,
  enabled BIT DEFAULT 1
);

ALTER TABLE opportunity_component ADD environment INT REFERENCES lookup_opportunity_environment(code),
ALTER TABLE opportunity_component ADD competitors INT REFERENCES lookup_opportunity_competitors(code),
ALTER TABLE opportunity_component ADD compelling_event INT REFERENCES lookup_opportunity_event_compelling(code),
ALTER TABLE opportunity_component ADD budget INT REFERENCES lookup_opportunity_budget(code)

