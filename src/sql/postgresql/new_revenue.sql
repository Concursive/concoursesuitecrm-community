CREATE TABLE revenue (
  id serial PRIMARY KEY,
  org_id int default -1,
  transaction_id int default -1,
  month int default -1,
  year int default -1,
  amount float default 0,
  type int default -1,
  owner int default -1,
  description VARCHAR(255),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);
 
CREATE TABLE lookup_revenue_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

INSERT INTO lookup_revenue_types (description) VALUES ('Technical');

CREATE TABLE lookup_revenuedetail_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE revenue_detail (
  id serial PRIMARY KEY,
  revenue_id int default -1,
  amount float default 0,
  type int default -1,
  owner int default -1,
  description VARCHAR(255),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);

CREATE TABLE lookup_months (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;
INSERT INTO lookup_months (description, level) VALUES ('January', 10);
INSERT INTO lookup_months (description, level) VALUES ('February', 20);
INSERT INTO lookup_months (description, level) VALUES ('March', 30);
INSERT INTO lookup_months (description, level) VALUES ('April', 40);
INSERT INTO lookup_months (description, level) VALUES ('May', 50);
INSERT INTO lookup_months (description, level) VALUES ('June', 60);
INSERT INTO lookup_months (description, level) VALUES ('July', 70);
INSERT INTO lookup_months (description, level) VALUES ('August', 80);
INSERT INTO lookup_months (description, level) VALUES ('September', 90);
INSERT INTO lookup_months (description, level) VALUES ('October', 100);
INSERT INTO lookup_months (description, level) VALUES ('November', 110);
INSERT INTO lookup_months (description, level) VALUES ('December', 120);

