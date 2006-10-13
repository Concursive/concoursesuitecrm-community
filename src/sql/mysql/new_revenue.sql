-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

CREATE TABLE lookup_revenue_types (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE lookup_revenuedetail_types (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
)
;

CREATE TABLE revenue (
  id INT AUTO_INCREMENT PRIMARY KEY,
  org_id INT REFERENCES organization(org_id),
  transaction_id INT DEFAULT -1,
  month INT DEFAULT -1,
  year INT DEFAULT -1,
  amount FLOAT DEFAULT 0,
  type INT REFERENCES lookup_revenue_types(code),
  owner INT REFERENCES `access`(user_id),
  description VARCHAR(255),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

CREATE TABLE revenue_detail (
  id INT AUTO_INCREMENT PRIMARY KEY,
  revenue_id INT REFERENCES revenue(id),
  amount FLOAT DEFAULT 0,
  type INT REFERENCES lookup_revenue_types(code),
  owner INT REFERENCES `access`(user_id),
  description VARCHAR(255),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);


