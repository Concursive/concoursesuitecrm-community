CREATE SEQUENCE web_site_access_log_id_seq;
CREATE TABLE web_site_access_log (
  site_log_id INT,
  site_id INT REFERENCES web_site(site_id),
  user_id INT REFERENCES access(user_id),
  ip NVARCHAR2(300),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  browser NVARCHAR2(255),
  referrer NVARCHAR2(1024),
  PRIMARY KEY(SITE_LOG_ID)
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
  emails_to CLOD NOT NULL,
  from_name NVARCHAR2(300) NOT NULL,
  comments NVARCHAR2(1024),
  site_log_id INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);