CREATE SEQUENCE web_layout_layout_id_seq;
CREATE TABLE web_layout (
  layout_id INTEGER NOT NULL PRIMARY KEY,
  layout_constant INTEGER,
  layout_name VARGRAPHIC(300) NOT NULL,
  jsp VARGRAPHIC(300),
  thumbnail VARGRAPHIC(300),
  custom CHAR(1) NOT NULL DEFAULT '0'
);

CREATE SEQUENCE web_style_style_id_seq;
CREATE TABLE web_style (
  style_id INTEGER NOT NULL PRIMARY KEY,
  style_constant INTEGER,
  style_name VARGRAPHIC(300) NOT NULL,
  css VARGRAPHIC(300),
  thumbnail VARGRAPHIC(300),
  custom CHAR(1) NOT NULL DEFAULT '0',
  layout_id INTEGER REFERENCES web_layout(layout_id)
);

CREATE SEQUENCE web_site_site_id_seq;
CREATE TABLE web_site (
  site_id INTEGER NOT NULL PRIMARY KEY,
  site_name VARGRAPHIC(300) NOT NULL,
  internal_description CLOB(2G) NOT LOGGED,
  hit_count INT,
  notes CLOB(2G) NOT LOGGED,
  enabled CHAR(1) NOT NULL DEFAULT '1',
  layout_id INTEGER REFERENCES web_layout(layout_id),
  style_id INTEGER REFERENCES web_style(style_id),
	logo_image_id INTEGER REFERENCES project_files(item_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

CREATE SEQUENCE web_site_log_site_log_id_seq;
CREATE TABLE web_site_log (
  site_log_id INTEGER NOT NULL PRIMARY KEY,
  site_id INTEGER REFERENCES web_site(site_id),
  user_id INTEGER REFERENCES "access"(user_id),
  username VARGRAPHIC(80) NOT NULL,
  ip VARGRAPHIC(80),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  browser VARGRAPHIC(255)
);

CREATE SEQUENCE web_tab_tab_id_seq;
CREATE TABLE web_tab (
  tab_id INTEGER NOT NULL PRIMARY KEY,
  display_text VARGRAPHIC(300) NOT NULL,
  internal_description CLOB(2G) NOT LOGGED,
  site_id INTEGER REFERENCES web_site(site_id),
  tab_position INTEGER NOT NULL,
  enabled CHAR(1) NOT NULL DEFAULT '1',
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

CREATE SEQUENCE web_page_vers_e_version_id_seq;
CREATE TABLE web_page_version (
  page_version_id INTEGER NOT NULL PRIMARY KEY,
  version_number INTEGER NOT NULL,
  internal_description CLOB(2G) NOT LOGGED,
  notes CLOB(2G) NOT LOGGED,
  parent_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

CREATE SEQUENCE web_page_grou_age_group_id_seq;
CREATE TABLE web_page_group (
  page_group_id INTEGER NOT NULL PRIMARY KEY,
  group_name VARGRAPHIC(300),
  internal_description CLOB(2G) NOT LOGGED,
  group_position INTEGER NOT NULL,
  tab_id INTEGER REFERENCES web_tab(tab_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

CREATE SEQUENCE web_tab_banne_ab_banner_id_seq;
CREATE TABLE web_tab_banner (
  tab_banner_id INTEGER NOT NULL PRIMARY KEY,
  tab_id INTEGER REFERENCES web_tab(tab_id),
  image_id INTEGER REFERENCES project_files(item_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

CREATE SEQUENCE web_page_page_id_seq;
CREATE TABLE web_page (
  page_id INTEGER NOT NULL PRIMARY KEY,
  page_name VARGRAPHIC(300),
  page_position INTEGER NOT NULL,
  active_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  construction_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  page_group_id INTEGER REFERENCES web_page_group(page_group_id),
  tab_banner_id INTEGER REFERENCES web_tab_banner(tab_banner_id),
  notes CLOB(2G) NOT LOGGED,
  enabled CHAR(1) NOT NULL DEFAULT '1',
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

ALTER TABLE web_page_version ADD COLUMN page_id INTEGER REFERENCES web_page(page_id);

CREATE SEQUENCE web_page_row_page_row_id_seq;
CREATE TABLE web_page_row (
  page_row_id INTEGER NOT NULL PRIMARY KEY,
  row_position INTEGER NOT NULL,
  page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  enabled CHAR(1) NOT NULL DEFAULT '1',
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

CREATE SEQUENCE web_icelet_icelet_id_seq;
CREATE TABLE web_icelet (
  icelet_id INTEGER NOT NULL PRIMARY KEY,
  icelet_name VARGRAPHIC(300) NOT NULL,
  icelet_description CLOB(2G) NOT LOGGED,
  icelet_configurator_class VARGRAPHIC(300) NOT NULL UNIQUE,
  icelet_version INT,
  enabled CHAR(1) NOT NULL DEFAULT '1'
);

CREATE SEQUENCE web_row_colum_ow_column_id_seq;
CREATE TABLE web_row_column (
  row_column_id INTEGER NOT NULL PRIMARY KEY,
  column_position INTEGER NOT NULL,
  width INT,
  page_row_id INTEGER REFERENCES web_page_row(page_row_id),
  icelet_id INTEGER REFERENCES web_icelet(icelet_id),
  enabled CHAR(1) NOT NULL DEFAULT '1',
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

ALTER TABLE web_page_row ADD COLUMN row_column_id INTEGER REFERENCES web_row_column(row_column_id);

CREATE SEQUENCE web_icelet_pr__property_id_seq;
CREATE TABLE web_icelet_property (
  property_id INTEGER NOT NULL PRIMARY KEY,
  property_type_constant INT,
  property_value CLOB(2G) NOT LOGGED,
  row_column_id INTEGER NOT NULL REFERENCES web_row_column(row_column_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

CREATE SEQUENCE portfolio_cat__category_id_seq;
CREATE TABLE portfolio_category (
  category_id INTEGER NOT NULL PRIMARY KEY,
  category_name VARGRAPHIC(300) NOT NULL,
  category_description CLOB(2G) NOT LOGGED,
  category_position_id INTEGER REFERENCES portfolio_category(category_id),
  parent_category_id INTEGER REFERENCES portfolio_category(category_id),
  enabled CHAR(1) NOT NULL DEFAULT '1',
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

CREATE SEQUENCE portfolio_item_item_id_seq;
CREATE TABLE portfolio_item (
  item_id INTEGER NOT NULL PRIMARY KEY,
  item_name VARGRAPHIC(300) NOT NULL,
  item_description CLOB(2G) NOT LOGGED,
  item_position_id INTEGER REFERENCES portfolio_item(item_id),
  image_id INTEGER REFERENCES project_files(item_id),
  caption VARGRAPHIC(300),
  portfolio_category_id INTEGER REFERENCES portfolio_category(category_id),
  enabled CHAR(1) NOT NULL DEFAULT '1',
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id)
);

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

CREATE SEQUENCE url_map_url_id_seq;
CREATE TABLE url_map (
  url_id INTEGER NOT NULL PRIMARY KEY,
  time_in_millis DECIMAL NOT NULL,
  url CLOB(2G) NOT LOGGED
);
