CREATE TABLE lookup_revenue_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE lookup_revenuedetail_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE revenue (
  id serial PRIMARY KEY,
  org_id int references organization(org_id),
  transaction_id int default -1,
  month int default -1,
  year int default -1,
  amount float default 0,
  type int references lookup_revenue_types(code),
  owner int references access(user_id),
  description VARCHAR(255),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id)
);

CREATE TABLE revenue_detail (
  id serial PRIMARY KEY,
  revenue_id int references revenue(id),
  amount float default 0,
  type int references lookup_revenue_types(code),
  owner int references access(user_id),
  description VARCHAR(255),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id)
);


