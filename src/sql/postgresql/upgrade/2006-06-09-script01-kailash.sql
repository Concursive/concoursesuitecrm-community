--DROP TABLE web_site_log;
CREATE TABLE web_site_access_log (
  site_log_id SERIAL PRIMARY KEY,
  site_id INT NOT NULL REFERENCES web_site(site_id),
  user_id INT NOT NULL REFERENCES access(user_id),
  ip VARCHAR(300),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255)
);

CREATE TABLE web_page_access_log (
  page_id INT NOT NULL REFERENCES web_page(page_id),
  site_log_id INT NOT NULL REFERENCES web_site_access_log(site_log_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE web_product_access_log (
  product_id INT NOT NULL REFERENCES product_catalog(product_id),
  site_log_id INT NOT NULL REFERENCES web_site_access_log(site_log_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE web_product_email_log (
  product_id INT NOT NULL REFERENCES product_catalog(product_id),
	emails_to TEXT NOT NULL,
	from_name VARCHAR(300) NOT NULL,
	comments VARCHAR(1024),
  site_log_id INT NOT NULL REFERENCES web_site_access_log(site_log_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

