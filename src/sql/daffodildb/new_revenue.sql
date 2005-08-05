

CREATE SEQUENCE lookup_revenue_types_code_seq;
CREATE TABLE lookup_revenue_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_revenuedetail_t_code_seq;
CREATE TABLE lookup_revenuedetail_types (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE revenue_id_seq;
CREATE TABLE revenue (
  id INT  PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  transaction_id INT DEFAULT -1,
  "month" INT DEFAULT -1,
  "year" INT DEFAULT -1,
  amount FLOAT DEFAULT 0,
  type INT REFERENCES lookup_revenue_types(code),
  owner INT REFERENCES access(user_id),
  description VARCHAR(255),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL references access(user_id)
);

CREATE SEQUENCE revenue_detail_id_seq;
CREATE TABLE revenue_detail (
  id INT  PRIMARY KEY,
  revenue_id INT REFERENCES revenue(id),
  amount FLOAT DEFAULT 0,
  type INT REFERENCES lookup_revenue_types(code),
  owner INT REFERENCES access(user_id),
  description VARCHAR(255),
  entered TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INT NOT NULL references access(user_id)
);


