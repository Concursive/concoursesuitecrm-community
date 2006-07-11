-- Optimized to reduce database overhead
CREATE GENERATOR web_site_access_log_id_seq;
CREATE TABLE web_site_access_log (
  site_log_id INTEGER,
  site_id INT REFERENCES web_site(site_id),
  user_id INT REFERENCES access(user_id),
  ip VARCHAR(300),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255),
  referrer VARCHAR(1024),
  PRIMARY KEY (SITE_LOG_ID)
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
	emails_to  BLOB SUB_TYPE 1 SEGMENT SIZE 100 NOT NULL,
	from_name VARCHAR(300) NOT NULL,
	comments VARCHAR(1024),
  site_log_id INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



