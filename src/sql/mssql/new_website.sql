/**
 *  MSSQL Table Creation
 *
 *@version    $Id: new_website.sql 14246 2006-02-15 21:06:34Z mrajkowski $
 */
CREATE TABLE web_layout (
  layout_id INT IDENTITY PRIMARY KEY,
  layout_constant INT UNIQUE,
  layout_name VARCHAR(300) NOT NULL,
  jsp VARCHAR(300),
  thumbnail VARCHAR(300),
  custom BIT NOT NULL DEFAULT 0
);

CREATE TABLE web_style (
  style_id INT IDENTITY PRIMARY KEY,
  style_constant INT UNIQUE,
  style_name VARCHAR(300) NOT NULL,
  css VARCHAR(300),
  thumbnail VARCHAR(300),
  custom BIT NOT NULL DEFAULT 0,
  layout_id INT REFERENCES web_layout(layout_id)
);

CREATE TABLE web_site (
  site_id INT IDENTITY PRIMARY KEY,
  site_name VARCHAR(300) NOT NULL,
  internal_description TEXT,
  hit_count INT,
  notes TEXT,
  enabled BIT NOT NULL DEFAULT 1,
  layout_id INT REFERENCES web_layout(layout_id),
  style_id INT REFERENCES web_style(style_id),
	logo_image_id INT REFERENCES project_files(item_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE web_site_log (
  site_log_id INT IDENTITY PRIMARY KEY,
  site_id INT references web_site(site_id),
  user_id INT references access(user_id),
  username VARCHAR(80) NOT NULL,
  ip VARCHAR(80),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255)
);

CREATE TABLE web_tab (
  tab_id INT IDENTITY PRIMARY KEY,
  display_text VARCHAR(300) NOT NULL,
  internal_description TEXT,
  site_id INT references web_site(site_id),
  tab_position INT NOT NULL,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE web_page_version (
  page_version_id INT IDENTITY PRIMARY KEY,
  version_number INT NOT NULL,
  internal_description TEXT,
  notes TEXT,
  parent_page_version_id INT REFERENCES web_page_version(page_version_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE web_page_group (
  page_group_id INT IDENTITY PRIMARY KEY,
  group_name VARCHAR(300),
  internal_description TEXT,
  group_position INT NOT NULL,
  tab_id INT REFERENCES web_tab(tab_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE web_tab_banner (
  tab_banner_id INT IDENTITY PRIMARY KEY,
  tab_id INT REFERENCES web_tab(tab_id),
  image_id INT REFERENCES project_files(item_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE web_page (
  page_id INT IDENTITY PRIMARY KEY,
  page_name VARCHAR(300),
  page_position INT NOT NULL,
  active_page_version_id INT REFERENCES web_page_version(page_version_id),
  construction_page_version_id INT REFERENCES web_page_version(page_version_id),
  page_group_id INT REFERENCES web_page_group(page_group_id),
  tab_banner_id INT REFERENCES web_tab_banner(tab_banner_id),
  notes TEXT,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

ALTER TABLE web_page_version ADD page_id INT REFERENCES web_page(page_id);

CREATE TABLE web_page_row (
  page_row_id INT IDENTITY PRIMARY KEY,
  row_position INT NOT NULL,
  page_version_id INT REFERENCES web_page_version(page_version_id),
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE web_icelet (
  icelet_id INT IDENTITY PRIMARY KEY,
  icelet_name VARCHAR(300) NOT NULL,
  icelet_description TEXT,
  icelet_configurator_class VARCHAR(300) NOT NULL UNIQUE,
  icelet_version INT,
  enabled BIT NOT NULL DEFAULT 1
);

CREATE TABLE web_row_column (
  row_column_id INT IDENTITY PRIMARY KEY,
  column_position INT NOT NULL,
  width INT,
  page_row_id INT REFERENCES web_page_row(page_row_id),
  icelet_id INT REFERENCES web_icelet(icelet_id),
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

ALTER TABLE web_page_row ADD row_column_id INT REFERENCES web_row_column(row_column_id);

CREATE TABLE web_icelet_property (
  property_id INT IDENTITY PRIMARY KEY,
  property_type_constant INT,
  property_value TEXT,
  row_column_id INT NOT NULL REFERENCES web_row_column(row_column_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE portfolio_category (
  category_id INT IDENTITY PRIMARY KEY,
  category_name VARCHAR(300) NOT NULL,
  category_description TEXT,
  category_position_id INT REFERENCES portfolio_category(category_id),
  parent_category_id INT REFERENCES portfolio_category(category_id),
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE portfolio_item (
  item_id INT IDENTITY PRIMARY KEY,
  item_name VARCHAR(300) NOT NULL,
  item_description TEXT,
  item_position_id INT REFERENCES portfolio_item(item_id),
  image_id INT REFERENCES project_files(item_id),
  caption VARCHAR(300),
  portfolio_category_id INT REFERENCES portfolio_category(category_id),
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

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

CREATE TABLE url_map (
  url_id INT IDENTITY PRIMARY KEY,
  time_in_millis INT NOT NULL,
  url TEXT
);
