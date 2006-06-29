CREATE SEQUENCE web_site_acce__site_log_id_seq;
CREATE TABLE web_site_access_log (
  site_log_id INT NOT NULL PRIMARY KEY,
  site_id INT REFERENCES web_site(site_id),
  user_id INT REFERENCES "access"(user_id),
  ip VARGRAPHIC(300),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  browser VARGRAPHIC(255),
  referrer VARGRAPHIC(1024)
);

CREATE TABLE web_page_access_log (
  page_id INT,
  site_log_id INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE web_product_access_log (
  product_id INT,
  site_log_id INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE web_product_email_log (
  product_id INT,
	emails_to CLOB(2G) NOT LOGGED NOT NULL,
	from_name VARGRAPHIC(300) NOT NULL,
	comments VARGRAPHIC(1024),
  site_log_id INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
