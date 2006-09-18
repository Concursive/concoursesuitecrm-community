
CREATE SEQUENCE web_layout_layout_id_seq;
CREATE TABLE web_layout (
  layout_id INTEGER NOT NULL,
  layout_constant INTEGER,
  layout_name NVARCHAR2(300) NOT NULL,
  jsp NVARCHAR2(300),
  thumbnail NVARCHAR2(300),
  custom CHAR(1) DEFAULT 0 NOT NULL,
  PRIMARY KEY (LAYOUT_ID)
);

CREATE SEQUENCE web_style_style_id_seq;
CREATE TABLE web_style (
  style_id INTEGER NOT NULL,
  style_constant INTEGER,
  style_name NVARCHAR2(300) NOT NULL,
  css NVARCHAR2(300),
  thumbnail NVARCHAR2(300),
  custom CHAR(1) DEFAULT 0 NOT NULL,
  layout_id NUMBER(10,0)  REFERENCES web_layout(layout_id),
  PRIMARY KEY (STYLE_ID)
);

CREATE SEQUENCE web_site_site_id_seq;
CREATE TABLE web_site (
  site_id INTEGER NOT NULL,
  site_name NVARCHAR2(300) NOT NULL,
  internal_description CLOB,
  hit_count INTEGER,
  notes CLOB,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  layout_id INTEGER REFERENCES web_layout(layout_id),
  style_id INTEGER REFERENCES web_style(style_id),
  logo_image_id INTEGER REFERENCES project_files (item_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (SITE_ID)
);

CREATE SEQUENCE web_site_log_site_log_id_seq;
CREATE TABLE web_site_log (
  site_log_id INTEGER NOT NULL,
  site_id INTEGER REFERENCES web_site(site_id),
  user_id INTEGER REFERENCES access(user_id),
  username NVARCHAR2(80) NOT NULL,
  ip NVARCHAR2(80),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  browser NVARCHAR2(255),
  PRIMARY KEY (SITE_LOG_ID)
);

CREATE SEQUENCE web_tab_tab_id_seq;
CREATE TABLE web_tab (
  tab_id INTEGER NOT NULL,
  display_text NVARCHAR2(300) NOT NULL,
  internal_description CLOB,
  site_id INTEGER REFERENCES web_site(site_id),
  tab_position INTEGER NOT NULL,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (TAB_ID)
);

CREATE SEQUENCE web_page_vers_e_version_id_seq;
CREATE TABLE web_page_version (
  page_version_id INTEGER NOT NULL,
  version_number INTEGER NOT NULL,
  internal_description CLOB,
  notes CLOB,
  parent_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (PAGE_VERSION_ID)
);

CREATE SEQUENCE web_page_grou_age_group_id_seq;
CREATE TABLE web_page_group (
  page_group_id INTEGER NOT NULL,
  group_name NVARCHAR2(300),
  internal_description CLOB,
  group_position INTEGER NOT NULL,
  tab_id INTEGER REFERENCES web_tab(tab_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (PAGE_GROUP_ID)
);

CREATE SEQUENCE web_tab_banne_ab_banner_id_seq;
CREATE TABLE web_tab_banner(
  tab_banner_id INTEGER NOT NULL,
  tab_id INTEGER REFERENCES web_tab(tab_id),
  image_id INTEGER REFERENCES project_files(item_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (TAB_BANNER_ID)
);

CREATE SEQUENCE web_page_id_page_id_seq;
CREATE TABLE web_page (
  page_id INTEGER NOT NULL,
  page_name NVARCHAR2(300),
  page_position INTEGER NOT NULL,
  active_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  construction_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  page_group_id INTEGER REFERENCES web_page_group(page_group_id),
  tab_banner_id INTEGER REFERENCES web_tab_banner(tab_banner_id),
  notes CLOB,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (PAGE_ID)
);

ALTER TABLE web_page_version ADD ( CONSTRAINT  fk__web_page___page___628fa481
 FOREIGN KEY ( page_id )  REFERENCES web_page ( page_id ) ON
 DELETE CASCADE );

CREATE SEQUENCE web_page_row_page_row_id_seq;
CREATE TABLE web_page_row (
  page_row_id INTEGER NOT NULL,
  row_position INTEGER NOT NULL,
  page_version_id INTEGER REFERENCES web_page_version( page_version_id),
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (PAGE_ROW_ID)
);

CREATE SEQUENCE web_icelet_icelet_id_seq;
CREATE TABLE web_icelet (
  icelet_id INTEGER  NOT NULL,
  icelet_name NVARCHAR2(300) NOT NULL,
  icelet_description CLOB,
  icelet_configurator_class NVARCHAR2(300) NOT NULL,
  icelet_version INTEGER,
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  PRIMARY KEY (ICELET_ID)
);

CREATE SEQUENCE web_row_column_ow_column_id_seq;
CREATE TABLE web_row_column (
  row_column_id INTEGER NOT NULL,
  column_position INTEGER NOT NULL,
  width INTEGER,
  page_row_id INTEGER REFERENCES web_page_row(page_row_id),
  icelet_id INTEGER REFERENCES web_icelet(icelet_id),
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (ROW_COLUMN_ID)
);

ALTER TABLE web_page_row ADD ( CONSTRAINT  fk__web_page___row_c__778ac167
 FOREIGN KEY ( row_column_id )  REFERENCES web_row_column (
 row_column_id ) ON DELETE CASCADE );

CREATE SEQUENCE web_icelet_pr__property_id_seq;
CREATE TABLE web_icelet_property (
  property_id INTEGER NOT NULL,
  property_type_constant INTEGER,
  property_value CLOB,
  row_column_id INTEGER NOT NULL REFERENCES web_row_column (row_column_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (PROPERTY_ID)
);

CREATE SEQUENCE portfolio_cat__category_id_seq;
CREATE TABLE portfolio_category (
  category_id INTEGER NOT NULL,
  category_name NVARCHAR2(300) NOT NULL,
  category_description CLOB,
  category_position_id INTEGER REFERENCES portfolio_category(category_id ),
  parent_category_id INTEGER REFERENCES portfolio_category(category_id),
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby NUMBER(10,0) NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (CATEGORY_ID)
);

CREATE SEQUENCE portfolio_item_item_id_seq;
CREATE TABLE portfolio_item (
  item_id INTEGER NOT NULL,
  item_name NVARCHAR2(300) NOT NULL,
  item_description CLOB,
  item_position_id INTEGER REFERENCES portfolio_item(item_id),
  image_id INTEGER REFERENCES project_files(item_id),
  caption NVARCHAR2(300),
  portfolio_category_id INTEGER REFERENCES portfolio_category(category_id),
  enabled CHAR(1) DEFAULT 1 NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id),
  PRIMARY KEY (ITEM_ID)
);

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
  emails_to CLOB NOT NULL,
  from_name NVARCHAR2(300) NOT NULL,
  comments NVARCHAR2(1024),
  site_log_id INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE url_map (
  url_id SERIAL PRIMARY KEY,
  time_in_millis INT NOT NULL,
  url TEXT
);
