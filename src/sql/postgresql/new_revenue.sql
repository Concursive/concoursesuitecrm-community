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
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);


