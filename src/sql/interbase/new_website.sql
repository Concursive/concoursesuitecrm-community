
CREATE GENERATOR web_layout_layout_id_seq;
CREATE TABLE web_layout (
  layout_id INTEGER NOT NULL,
  layout_constant INTEGER,
  layout_name VARCHAR(300) NOT NULL,
  jsp VARCHAR(300),
  thumbnail VARCHAR(300),
  custom BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (LAYOUT_ID)
);

CREATE GENERATOR web_style_style_id_seq;
CREATE TABLE web_style (
  style_id INTEGER NOT NULL,
  style_constant INTEGER,
  style_name VARCHAR(300) NOT NULL,
  css VARCHAR(300),
  thumbnail VARCHAR(300),
  custom BOOLEAN DEFAULT FALSE NOT NULL,
  layout_id INTEGER REFERENCES web_layout(layout_id),
  PRIMARY KEY (STYLE_ID)
);

CREATE GENERATOR web_site_site_id_seq;
CREATE TABLE web_site (
  site_id INTEGER NOT NULL,
  site_name VARCHAR(300) NOT NULL,
  internal_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  hit_count INTEGER,
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  layout_id INTEGER REFERENCES web_layout(layout_id),
  style_id INTEGER REFERENCES web_style(style_id),
  logo_image_id INTEGER REFERENCES project_files (item_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (SITE_ID)
);

CREATE GENERATOR web_site_log_site_log_id_seq;
CREATE TABLE web_site_log (
  site_log_id INTEGER NOT NULL,
  site_id INTEGER REFERENCES web_site(site_id),
  user_id INTEGER REFERENCES "access"(user_id),
  username VARCHAR(80) NOT NULL,
  ip VARCHAR(80),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  browser VARCHAR(255),
  PRIMARY KEY (SITE_LOG_ID)
);

CREATE GENERATOR web_tab_tab_id_seq;
CREATE TABLE web_tab (
  tab_id INTEGER NOT NULL,
  display_text VARCHAR(300) NOT NULL,
  internal_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  site_id INTEGER REFERENCES web_site(site_id),
  tab_position INTEGER NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (TAB_ID)
);

CREATE GENERATOR web_page_vers_ge_version_id_seq;
CREATE TABLE web_page_version (
  page_version_id INTEGER NOT NULL,
  version_number INTEGER NOT NULL,
  internal_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  parent_page_version_id INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PAGE_VERSION_ID)
);
-- self recursive in table def not allowed
alter table web_page_version add foreign key (parent_page_version_id) REFERENCES web_page_version(page_version_id);

CREATE GENERATOR web_page_grou_page_group_id_seq;
CREATE TABLE web_page_group (
  page_group_id INTEGER NOT NULL,
  group_name VARCHAR(300),
  internal_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  group_position INTEGER NOT NULL,
  tab_id INTEGER REFERENCES web_tab(tab_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PAGE_GROUP_ID)
);

CREATE GENERATOR web_tab_banne_tab_banner_id_seq;
CREATE TABLE web_tab_banner(
  tab_banner_id INTEGER NOT NULL,
  tab_id INTEGER REFERENCES web_tab(tab_id),
  image_id INTEGER REFERENCES project_files(item_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (TAB_BANNER_ID)
);

CREATE GENERATOR web_page_page_id_seq;
CREATE TABLE web_page (
  page_id INTEGER NOT NULL,
  page_name VARCHAR(300),
  page_position INTEGER NOT NULL,
  active_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  construction_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  page_group_id INTEGER REFERENCES web_page_group(page_group_id),
  tab_banner_id INTEGER REFERENCES web_tab_banner(tab_banner_id),
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PAGE_ID)
);

ALTER TABLE web_page_version ADD page_id INTEGER REFERENCES web_page(page_id);

CREATE GENERATOR web_page_row_page_row_id_seq;
CREATE TABLE web_page_row (
  page_row_id INTEGER NOT NULL,
  row_position INTEGER NOT NULL,
  page_version_id INTEGER REFERENCES web_page_version( page_version_id),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PAGE_ROW_ID)
);

CREATE GENERATOR web_icelet_icelet_id_seq;
CREATE TABLE web_icelet (
  icelet_id INTEGER  NOT NULL,
  icelet_name VARCHAR(300) NOT NULL,
  icelet_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  icelet_configurator_class VARCHAR(300) NOT NULL,
  icelet_version INTEGER,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  PRIMARY KEY (ICELET_ID)
);

CREATE GENERATOR web_row_colum_row_column_id_seq;
CREATE TABLE web_row_column (
  row_column_id INTEGER NOT NULL,
  column_position INTEGER NOT NULL,
  width INTEGER,
  page_row_id INTEGER REFERENCES web_page_row(page_row_id),
  icelet_id INTEGER REFERENCES web_icelet(icelet_id),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ROW_COLUMN_ID)
);

ALTER TABLE web_page_row ADD row_column_id INTEGER REFERENCES web_row_column(row_column_id);

CREATE GENERATOR web_icelet_pr_y_property_id_seq;
CREATE TABLE web_icelet_property (
  property_id INTEGER NOT NULL,
  property_type_constant INTEGER,
  property_value BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  row_column_id INTEGER NOT NULL REFERENCES web_row_column (row_column_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PROPERTY_ID)
);

CREATE GENERATOR portfolio_cat_y_category_id_seq;
CREATE TABLE portfolio_category (
  category_id INTEGER NOT NULL,
  category_name VARCHAR(300) NOT NULL,
  category_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  category_position_id INTEGER,
  parent_category_id INTEGER,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (CATEGORY_ID)
);

alter table portfolio_category add foreign key (category_position_id)  REFERENCES portfolio_category(category_id);
alter table portfolio_category add foreign key (parent_category_id)  REFERENCES portfolio_category(category_id);

CREATE GENERATOR portfolio_item_item_id_seq;
CREATE TABLE portfolio_item (
  item_id INTEGER NOT NULL,
  item_name VARCHAR(300) NOT NULL,
  item_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  item_position_id INTEGER,
  image_id INTEGER REFERENCES project_files(item_id),
  caption VARCHAR(300),
  portfolio_category_id INTEGER REFERENCES portfolio_category(category_id),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ITEM_ID)
);

alter table portfolio_item add foreign key (item_position_id) references portfolio_item(item_id);

CREATE GENERATOR web_site_acce_g_site_log_id_seq;
CREATE TABLE web_site_access_log (
  site_log_id INTEGER not null,
  site_id INTEGER REFERENCES web_site(site_id),
  user_id INTEGER REFERENCES "access"(user_id),
  ip VARCHAR(300),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255),
  referrer VARCHAR(1024),
  PRIMARY KEY(SITE_LOG_ID)
);

CREATE TABLE web_page_access_log (
  page_id INTEGER,
  site_log_id INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE web_product_access_log (
  product_id INTEGER,
  site_log_id INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE web_product_email_log (
  product_id INTEGER,
  emails_to BLOB SUB_TYPE 1 SEGMENT SIZE 100 NOT NULL,
  from_name VARCHAR(300) NOT NULL,
  comments VARCHAR(1024),
  site_log_id INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE GENERATOR url_map_url_id_seq;
CREATE TABLE url_map (
  url_id INTEGER NOT NULL PRIMARY KEY,
  time_in_millis DECIMAL NOT NULL,
  url BLOB SUB_TYPE 1 SEGMENT SIZE 100
);

CREATE GENERATOR web_layout_layout_id_seq;
CREATE TABLE web_layout (
  layout_id INTEGER NOT NULL,
  layout_constant INTEGER,
  layout_name VARCHAR(300) NOT NULL,
  jsp VARCHAR(300),
  thumbnail VARCHAR(300),
  custom BOOLEAN DEFAULT FALSE NOT NULL,
  PRIMARY KEY (LAYOUT_ID)
);

CREATE GENERATOR web_style_style_id_seq;
CREATE TABLE web_style (
  style_id INTEGER NOT NULL,
  style_constant INTEGER,
  style_name VARCHAR(300) NOT NULL,
  css VARCHAR(300),
  thumbnail VARCHAR(300),
  custom BOOLEAN DEFAULT FALSE NOT NULL,
  layout_id INTEGER REFERENCES web_layout(layout_id),
  PRIMARY KEY (STYLE_ID)
);

CREATE GENERATOR web_site_site_id_seq;
CREATE TABLE web_site (
  site_id INTEGER NOT NULL,
  site_name VARCHAR(300) NOT NULL,
  internal_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  hit_count INTEGER,
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  layout_id INTEGER REFERENCES web_layout(layout_id),
  style_id INTEGER REFERENCES web_style(style_id),
  logo_image_id INTEGER REFERENCES project_files (item_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (SITE_ID)
);

CREATE GENERATOR web_site_log_site_log_id_seq;
CREATE TABLE web_site_log (
  site_log_id INTEGER NOT NULL,
  site_id INTEGER REFERENCES web_site(site_id),
  user_id INTEGER REFERENCES "access"(user_id),
  username VARCHAR(80) NOT NULL,
  ip VARCHAR(80),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  browser VARCHAR(255),
  PRIMARY KEY (SITE_LOG_ID)
);

CREATE GENERATOR web_tab_tab_id_seq;
CREATE TABLE web_tab (
  tab_id INTEGER NOT NULL,
  display_text VARCHAR(300) NOT NULL,
  internal_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  site_id INTEGER REFERENCES web_site(site_id),
  tab_position INTEGER NOT NULL,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (TAB_ID)
);

CREATE GENERATOR web_page_vers_ge_version_id_seq;
CREATE TABLE web_page_version (
  page_version_id INTEGER NOT NULL,
  version_number INTEGER NOT NULL,
  internal_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  parent_page_version_id INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PAGE_VERSION_ID)
);
-- self recursive in table def not allowed
alter table web_page_version add foreign key (parent_page_version_id) REFERENCES web_page_version(page_version_id);

CREATE GENERATOR web_page_grou_page_group_id_seq;
CREATE TABLE web_page_group (
  page_group_id INTEGER NOT NULL,
  group_name VARCHAR(300),
  internal_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  group_position INTEGER NOT NULL,
  tab_id INTEGER REFERENCES web_tab(tab_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PAGE_GROUP_ID)
);

CREATE GENERATOR web_tab_banne_tab_banner_id_seq;
CREATE TABLE web_tab_banner(
  tab_banner_id INTEGER NOT NULL,
  tab_id INTEGER REFERENCES web_tab(tab_id),
  image_id INTEGER REFERENCES project_files(item_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (TAB_BANNER_ID)
);

CREATE GENERATOR web_page_page_id_seq;
CREATE TABLE web_page (
  page_id INTEGER NOT NULL,
  page_name VARCHAR(300),
  page_position INTEGER NOT NULL,
  active_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  construction_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  page_group_id INTEGER REFERENCES web_page_group(page_group_id),
  tab_banner_id INTEGER REFERENCES web_tab_banner(tab_banner_id),
  notes BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PAGE_ID)
);

ALTER TABLE web_page_version ADD page_id INTEGER REFERENCES web_page(page_id);

CREATE GENERATOR web_page_row_page_row_id_seq;
CREATE TABLE web_page_row (
  page_row_id INTEGER NOT NULL,
  row_position INTEGER NOT NULL,
  page_version_id INTEGER REFERENCES web_page_version( page_version_id),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PAGE_ROW_ID)
);

CREATE GENERATOR web_icelet_icelet_id_seq;
CREATE TABLE web_icelet (
  icelet_id INTEGER  NOT NULL,
  icelet_name VARCHAR(300) NOT NULL,
  icelet_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  icelet_configurator_class VARCHAR(300) NOT NULL,
  icelet_version INTEGER,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  PRIMARY KEY (ICELET_ID)
);

CREATE GENERATOR web_row_colum_row_column_id_seq;
CREATE TABLE web_row_column (
  row_column_id INTEGER NOT NULL,
  column_position INTEGER NOT NULL,
  width INTEGER,
  page_row_id INTEGER REFERENCES web_page_row(page_row_id),
  icelet_id INTEGER REFERENCES web_icelet(icelet_id),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ROW_COLUMN_ID)
);

ALTER TABLE web_page_row ADD row_column_id INTEGER REFERENCES web_row_column(row_column_id);

CREATE GENERATOR web_icelet_pr_y_property_id_seq;
CREATE TABLE web_icelet_property (
  property_id INTEGER NOT NULL,
  property_type_constant INTEGER,
  property_value BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  row_column_id INTEGER NOT NULL REFERENCES web_row_column (row_column_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (PROPERTY_ID)
);

CREATE GENERATOR portfolio_cat_y_category_id_seq;
CREATE TABLE portfolio_category (
  category_id INTEGER NOT NULL,
  category_name VARCHAR(300) NOT NULL,
  category_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  category_position_id INTEGER,
  parent_category_id INTEGER,
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (CATEGORY_ID)
);

alter table portfolio_category add foreign key (category_position_id)  REFERENCES portfolio_category(category_id);
alter table portfolio_category add foreign key (parent_category_id)  REFERENCES portfolio_category(category_id);

CREATE GENERATOR portfolio_item_item_id_seq;
CREATE TABLE portfolio_item (
  item_id INTEGER NOT NULL,
  item_name VARCHAR(300) NOT NULL,
  item_description BLOB SUB_TYPE 1 SEGMENT SIZE 100,
  item_position_id INTEGER,
  image_id INTEGER REFERENCES project_files(item_id),
  caption VARCHAR(300),
  portfolio_category_id INTEGER REFERENCES portfolio_category(category_id),
  enabled BOOLEAN DEFAULT TRUE NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER NOT NULL REFERENCES "access"(user_id),
  PRIMARY KEY (ITEM_ID)
);

alter table portfolio_item add foreign key (item_position_id) references portfolio_item(item_id);

CREATE GENERATOR web_site_acce_g_site_log_id_seq;
CREATE TABLE web_site_access_log (
  site_log_id INTEGER not null,
  site_id INTEGER REFERENCES web_site(site_id),
  user_id INTEGER REFERENCES "access"(user_id),
  ip VARCHAR(300),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255),
  referrer VARCHAR(1024),
  PRIMARY KEY(SITE_LOG_ID)
);

CREATE TABLE web_page_access_log (
  page_id INTEGER,
  site_log_id INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE web_product_access_log (
  product_id INTEGER,
  site_log_id INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE web_product_email_log (
  product_id INTEGER,
  emails_to BLOB SUB_TYPE 1 SEGMENT SIZE 100 NOT NULL,
  from_name VARCHAR(300) NOT NULL,
  comments VARCHAR(1024),
  site_log_id INTEGER,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE GENERATOR url_map_url_id_seq;
CREATE TABLE url_map (
  url_id INTEGER NOT NULL PRIMARY KEY,
  time_in_millis DECIMAL NOT NULL,
  url BLOB SUB_TYPE 1 SEGMENT SIZE 100
);
