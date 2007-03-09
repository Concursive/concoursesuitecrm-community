
CREATE SEQUENCE web_layout_layout_id_seq;
CREATE TABLE web_layout (
  layout_id INTEGER NOT NULL,
  layout_constant INTEGER,
  layout_name VARCHAR(300) NOT NULL,
  jsp VARCHAR(300),
  thumbnail VARCHAR(300),
  custom BOOLEAN DEFAULT false NOT NULL,
  PRIMARY KEY (LAYOUT_ID)
);

CREATE SEQUENCE web_style_style_id_seq;
CREATE TABLE web_style (
  style_id INTEGER NOT NULL,
  style_constant INTEGER,
  style_name VARCHAR(300) NOT NULL,
  css VARCHAR(300),
  thumbnail VARCHAR(300),
  custom BOOLEAN DEFAULT false NOT NULL,
  layout_id INTEGER REFERENCES web_layout(layout_id),
  PRIMARY KEY (STYLE_ID)
);

CREATE SEQUENCE web_site_site_id_seq;
CREATE TABLE web_site (
  site_id INTEGER NOT NULL,
  site_name VARCHAR(300) NOT NULL,
  internal_description CLOB,
  hit_count INTEGER,
  notes CLOB,
  enabled BOOLEAN DEFAULT true NOT NULL,
  layout_id INTEGER REFERENCES web_layout(layout_id),
  style_id INTEGER REFERENCES web_style(style_id),
  logo_image_id INTEGER REFERENCES project_files (item_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  PRIMARY KEY (SITE_ID)
);

CREATE SEQUENCE web_site_log_site_log_id_seq;
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

CREATE SEQUENCE web_tab_tab_id_seq;
CREATE TABLE web_tab (
  tab_id INTEGER NOT NULL,
  display_text VARCHAR(300) NOT NULL,
  internal_description CLOB,
  site_id INTEGER REFERENCES web_site(site_id),
  tab_position INTEGER NOT NULL,
  enabled BOOLEAN DEFAULT true NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  PRIMARY KEY (TAB_ID)
);

CREATE SEQUENCE web_page_version_page_version_id_seq;
CREATE TABLE web_page_version (
  page_version_id INTEGER NOT NULL,
  version_number INTEGER NOT NULL,
  internal_description CLOB,
  notes CLOB,
  parent_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  PRIMARY KEY (PAGE_VERSION_ID)
);

CREATE SEQUENCE web_page_group_page_group_id_seq;
CREATE TABLE web_page_group (
  page_group_id INTEGER NOT NULL,
  group_name VARCHAR(300),
  internal_description CLOB,
  group_position INTEGER NOT NULL,
  tab_id INTEGER REFERENCES web_tab(tab_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  PRIMARY KEY (PAGE_GROUP_ID)
);

CREATE SEQUENCE web_tab_banner_tab_banner_id_seq;
CREATE TABLE web_tab_banner (
  tab_banner_id INTEGER NOT NULL,
  tab_id INTEGER REFERENCES web_tab(tab_id),
  image_id INTEGER REFERENCES project_files(item_id),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  PRIMARY KEY (TAB_BANNER_ID)
);

CREATE SEQUENCE web_page_page_id_seq;
CREATE TABLE web_page (
  page_id INTEGER NOT NULL,
  page_name VARCHAR(300),
  page_position INTEGER NOT NULL,
  active_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  construction_page_version_id INTEGER REFERENCES web_page_version(page_version_id),
  page_group_id INTEGER REFERENCES web_page_group(page_group_id),
  tab_banner_id INTEGER REFERENCES web_tab_banner(tab_banner_id),
  notes CLOB,
  enabled BOOLEAN DEFAULT true NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  PRIMARY KEY (PAGE_ID)
);

ALTER TABLE web_page_version ADD page_id INTEGER REFERENCES web_page(page_id);

CREATE SEQUENCE web_page_row_page_row_id_seq;
CREATE TABLE web_page_row (
  page_row_id INTEGER NOT NULL,
  row_position INTEGER NOT NULL,
  page_version_id INTEGER REFERENCES web_page_version( page_version_id),
  enabled BOOLEAN DEFAULT true NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  PRIMARY KEY (PAGE_ROW_ID)
);

CREATE SEQUENCE web_icelet_icelet_id_seq;
CREATE TABLE web_icelet (
  icelet_id INTEGER  NOT NULL,
  icelet_name VARCHAR(300) NOT NULL,
  icelet_description CLOB,
  icelet_configurator_class VARCHAR(300) NOT NULL,
  icelet_version INTEGER,
  enabled BOOLEAN DEFAULT true NOT NULL,
  PRIMARY KEY (ICELET_ID)
);

CREATE SEQUENCE web_row_column_row_column_id_seq;
CREATE TABLE web_row_column (
  row_column_id INTEGER NOT NULL,
  column_position INTEGER NOT NULL,
  width INTEGER,
  page_row_id INTEGER REFERENCES web_page_row(page_row_id),
  icelet_id INTEGER REFERENCES web_icelet(icelet_id),
  enabled BOOLEAN DEFAULT true NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  PRIMARY KEY (ROW_COLUMN_ID)
);

ALTER TABLE web_page_row ADD row_column_id INTEGER REFERENCES web_row_column(row_column_id);

CREATE SEQUENCE web_icelet_property_property_id_seq;
CREATE TABLE web_icelet_property (
  property_id INTEGER NOT NULL,
  property_type_constant INTEGER,
  property_value CLOB,
  row_column_id INTEGER REFERENCES web_row_column (row_column_id) NOT NULL,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INTEGER REFERENCES "access"(user_id) NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifiedby INTEGER REFERENCES "access"(user_id) NOT NULL,
  PRIMARY KEY (PROPERTY_ID)
);