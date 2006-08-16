-- ----------------------------------------------------------------------------
--  PostgreSQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

CREATE TABLE netapp_contractexpiration(
  expiration_id INT AUTO_INCREMENT PRIMARY KEY,
  serial_number VARCHAR(255),
  agreement_number VARCHAR(255),
  services VARCHAR(1024),
  startdate TIMESTAMP NULL,
  enddate TIMESTAMP NULL,
  installed_at_company_name VARCHAR(1024),
  installed_at_site_name VARCHAR(1024),
  group_name VARCHAR(255),
  product_number VARCHAR(255),
  system_name VARCHAR(255),
  operating_system VARCHAR(255),
  no_of_shelves INT,
  no_of_disks INT,
  nvram INT,
  memory INT,
  auto_support_status VARCHAR(255),
  installed_at_address VARCHAR(1024),
  city VARCHAR(255),
  state_province VARCHAR(255),
  postal_code VARCHAR(255),
  country VARCHAR(255),
  installed_at_contact_firstname VARCHAR(255),
  contact_lastname VARCHAR(255),
  contact_email VARCHAR(255),
  agreement_company VARCHAR(255),
  quote_amount FLOAT,
  quotegenerateddate TIMESTAMP NULL,
  quoteaccepteddate TIMESTAMP NULL,
  quoterejecteddate TIMESTAMP NULL,
  comment TEXT,
  import_id INT,
  status_id INT,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INT NOT NULL REFERENCES `access`(user_id)
);

CREATE TABLE netapp_contractexpiration_log(
  id INT AUTO_INCREMENT PRIMARY KEY,
  expiration_id INT REFERENCES netapp_contractexpiration(expiration_id),
  quote_amount FLOAT,
  quotegenerateddate TIMESTAMP NULL,
  quoteaccepteddate TIMESTAMP NULL,
  quoterejecteddate TIMESTAMP NULL,
  comment TEXT,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INT NOT NULL REFERENCES `access`(user_id)
);