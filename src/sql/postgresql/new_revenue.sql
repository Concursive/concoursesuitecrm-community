/**
 *  PostgreSQL Table Creation
 *
 *@author     chris price
 *@version    $Id$
 */


CREATE TABLE lookup_revenue_types (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE SEQUENCE lookup_revenuedetail_t_code_seq;
CREATE TABLE lookup_revenuedetail_types (
  code INTEGER DEFAULT nextval('lookup_revenuedetail_t_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE revenue (
  id serial PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  transaction_id INT DEFAULT -1,
  month INT DEFAULT -1,
  year INT DEFAULT -1,
  amount FLOAT DEFAULT 0,
  type INT REFERENCES lookup_revenue_types(code),
  owner INT REFERENCES access(user_id),
  description VARCHAR(255),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id)
);

CREATE TABLE revenue_detail (
  id serial PRIMARY KEY,
  revenue_id INT REFERENCES revenue(id),
  amount FLOAT DEFAULT 0,
  type INT REFERENCES lookup_revenue_types(code),
  owner INT REFERENCES access(user_id),
  description VARCHAR(255),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id)
);


