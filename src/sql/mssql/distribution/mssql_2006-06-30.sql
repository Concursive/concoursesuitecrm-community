CREATE TABLE web_site_access_log (
  site_log_id INT IDENTITY PRIMARY KEY,
  site_id INT REFERENCES web_site(site_id),
  user_id INT REFERENCES access(user_id),
  ip VARCHAR(300),
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255),
  referrer VARCHAR(1024)
);

CREATE TABLE web_page_access_log (
  page_id INT,
  site_log_id INT,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE web_product_access_log (
  product_id INT,
  site_log_id INT,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE web_product_email_log (
  product_id INT,
	emails_to TEXT NOT NULL,
	from_name VARCHAR(300) NOT NULL,
	comments VARCHAR(1024),
  site_log_id INT,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
);
